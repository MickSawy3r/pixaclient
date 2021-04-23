package de.sixbits.pixaclient.main.repository

import android.util.Log
import de.sixbits.pixaclient.database.dao.CacheDao
import de.sixbits.pixaclient.database.utils.ImageEntityMapper
import de.sixbits.pixaclient.network.manager.PixabayManager
import de.sixbits.pixaclient.network.model.ImageDetailsModel
import de.sixbits.pixaclient.network.utils.ImageDetailsMapper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val TAG = "DetailsRepository"

class DetailsRepository @Inject constructor(
    private val pixabayManager: PixabayManager,
    private val cacheDao: CacheDao
) {

    /**
     * Get the details from the API and cache the result.
     * If the API has some error, get the cached version
     */
    fun getImageDetails(id: Int): Observable<ImageDetailsModel> {
        // When we get the data, cache it without consuming it.
        return this.pixabayManager.getImageDetails(id)
            .doOnNext {
                // Cache the result
                cacheDao.insert(ImageEntityMapper.fromImageDetailsModel(it))
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        Log.d(TAG, "getImageDetails: Cached!")
                    }
            }
    }
}