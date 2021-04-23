package de.sixbits.pixaclient.main.repository

import de.sixbits.pixaclient.database.dao.CacheDao
import de.sixbits.pixaclient.database.utils.ImageEntityMapper
import de.sixbits.pixaclient.network.manager.PixabayManager
import de.sixbits.pixaclient.network.model.ImageDetailsModel
import de.sixbits.pixaclient.network.utils.ImageDetailsMapper
import io.reactivex.Observable
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val pixabayManager: PixabayManager,
    private val cacheDao: CacheDao
) {

    /**
     * Get the details from the API and cache the result.
     * If the API has some error, get the cached version
     */
    fun getImageDetails(id: Int): Observable<ImageDetailsModel> {
        return this.pixabayManager.getImageDetails(id)
            .doOnNext {
                // Cache the result
                cacheDao.insert(ImageEntityMapper.fromImageDetailsModel(it))
            }.onErrorReturnItem(ImageDetailsMapper.fromImageEntity(cacheDao.getById(id)))
    }
}