package de.sixbits.pixaclient.main.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.sixbits.pixaclient.main.repository.MainRepository
import de.sixbits.pixaclient.network.model.ImageListItemModel
import io.americanexpress.busybee.BusyBee
import io.americanexpress.busybee.internal.BusyBeeSingleton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val busyBee = BusyBeeSingleton.singleton()

    private val compositeDisposable = CompositeDisposable()

    val searchImagesLiveData = MutableLiveData<List<ImageListItemModel>>()
    val pagerLiveData = MutableLiveData<List<ImageListItemModel>>()
    val loadingLiveData = MutableLiveData<Boolean>()

    private var activeSearchQuery = ""
    private var activePage = 1

    fun getCachedImages() {
        loadingLiveData.postValue(true)
        busyBee.busyWith("cache", BusyBee.Category.GENERAL)
        val compositeDisposable = CompositeDisposable()
        val cachedObservable = mainRepository.getCached()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                busyBee.completed("cache")
                searchImagesLiveData.postValue(it)
                loadingLiveData.postValue(false)
            }
        compositeDisposable.add(cachedObservable)
    }

    fun searchFor(query: String) {
        activeSearchQuery = query
        activePage = 1
        loadingLiveData.postValue(true)

        val compositeDisposable = CompositeDisposable()
        busyBee.busyWith(query, BusyBee.Category.NETWORK)
        val searchObservable = mainRepository.searchFor(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                busyBee.completed(query)
                searchImagesLiveData.postValue(it)
                loadingLiveData.postValue(false)
            }, {
                searchImagesLiveData.postValue(listOf())
            })
        compositeDisposable.add(searchObservable)
    }

    fun requestMoreImage() {
        activePage++
        busyBee.busyWith(activeSearchQuery, BusyBee.Category.NETWORK)
        val pagerObservable = mainRepository.requestSearchPage(activeSearchQuery, activePage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                busyBee.completed(activeSearchQuery)
                pagerLiveData.postValue(it)
            }, {
                pagerLiveData.postValue(listOf())
            })
        compositeDisposable.add(pagerObservable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
