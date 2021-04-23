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

    val imageListLiveData = MutableLiveData<List<ImageListItemModel>>()

    fun getCachedImages() {
        val compositeDisposable = CompositeDisposable()
        val cachedObservable = mainRepository.getCached()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                imageListLiveData.postValue(it)
            }
        compositeDisposable.add(cachedObservable)
    }
}