package de.sixbits.pixaclient.main.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.sixbits.pixaclient.main.repository.DetailsRepository
import de.sixbits.pixaclient.network.model.ImageDetailsModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val detailsRepository: DetailsRepository) :
    ViewModel() {

    val detailsLiveData = MutableLiveData<ImageDetailsModel>()

    fun getImageDetails(imageId: Int) {
        val disposable = CompositeDisposable()

        val repo = detailsRepository.getImageDetails(imageId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                detailsLiveData.postValue(it)
            }

        // a good developer always cleans up!
        disposable.add(repo)
    }
}