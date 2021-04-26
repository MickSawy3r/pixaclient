package de.sixbits.pixaclient.main.repository

import de.sixbits.pixaclient.database.dao.CacheDao
import de.sixbits.pixaclient.network.manager.PixabayManager
import de.sixbits.pixaclient.network.model.ImageListItemModel
import de.sixbits.pixaclient.network.utils.ImageListMapper
import io.reactivex.Observable
import javax.inject.Inject

open class MainRepository @Inject constructor(
    private val pixabayManager: PixabayManager,
    private val cacheDao: CacheDao
) {
    fun searchFor(query: String): Observable<List<ImageListItemModel>> {
        return this.pixabayManager.getSearchResult(query)
    }

    fun requestSearchPage(query: String, pageNumber: Int): Observable<List<ImageListItemModel>> {
        return this.pixabayManager.getSearchResultPage(query, pageNumber)
    }

    fun getCached(): Observable<List<ImageListItemModel>> {
        return this.cacheDao.getAll()
            .map {
                val cachedImages = mutableListOf<ImageListItemModel>()

                it.forEach { i -> cachedImages.add(ImageListMapper.fromImageEntity(i)) }

                return@map cachedImages
            }
    }
}