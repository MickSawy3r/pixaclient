package de.sixbits.pixaclient.main.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.sixbits.pixaclient.main.repository.MainRepository
import de.sixbits.pixaclient.network.model.ImageListItemModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val searchImagesLiveData = MutableLiveData<List<ImageListItemModel>>()
    val pagerLiveData = MutableLiveData<List<ImageListItemModel>>()
    val loadingLiveData = MutableLiveData<Boolean>()

    lateinit var activeSearchQuery: String
    var activePage = 1

    fun getCachedImages() {
        loadingLiveData.postValue(true)

        val compositeDisposable = CompositeDisposable()
        val cachedObservable = mainRepository.getCached()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
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
        val cachedObservable = mainRepository.searchFor(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                searchImagesLiveData.postValue(it)
                loadingLiveData.postValue(false)
            }, {
                searchImagesLiveData.postValue(listOf())
            })
        compositeDisposable.add(cachedObservable)
    }

    fun requestMore() {
        activePage++
        loadingLiveData.postValue(true)

        val compositeDisposable = CompositeDisposable()
        val cachedObservable = mainRepository.requestSearchPage(activeSearchQuery, activePage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                pagerLiveData.postValue(it)
            }, {
                pagerLiveData.postValue(listOf())
            })
        compositeDisposable.add(cachedObservable)
    }
}
