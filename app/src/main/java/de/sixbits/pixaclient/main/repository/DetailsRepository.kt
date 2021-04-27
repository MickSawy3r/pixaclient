package de.sixbits.pixaclient.main.repository

import android.app.Application
import de.sixbits.pixaclient.database.dao.CacheDao
import de.sixbits.pixaclient.database.utils.ImageEntityMapper
import de.sixbits.pixaclient.network.manager.PixabayManager
import de.sixbits.pixaclient.network.model.ImageDetailsModel
import de.sixbits.pixaclient.network.utils.ImageDetailsMapper
import de.sixbits.pixaclient.network.utils.NetworkUtils
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val pixabayManager: PixabayManager,
    private val cacheDao: CacheDao,
    private val application: Application,
    private val networkUtils: NetworkUtils
) {

    /**
     * Get the details from the API and cache the result.
     * If the API has some error, get the cached version
     */
    fun getImageDetails(id: Int): Observable<ImageDetailsModel> {
        // When we get the data, cache it without consuming it.

        if (networkUtils.isInternetAvailable()) {
            return this.pixabayManager.getImageDetails(id)
                .doOnNext {
                    // Cache the result
                    cacheDao.insert(ImageEntityMapper.fromImageDetailsModel(it))
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe()
                }
        } else {
            return this.cacheDao.getById(id).map {
                return@map ImageDetailsMapper.fromImageEntity(it)
            }
        }
    }
}