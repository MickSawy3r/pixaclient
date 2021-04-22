package de.sixbits.pixaclient.network.manager

import de.sixbits.pixaclient.BuildConfig
import de.sixbits.pixaclient.network.model.ImageListItemModel
import de.sixbits.pixaclient.network.service.PixabayService
import io.reactivex.Observable
import javax.inject.Inject

class PixabayManager @Inject constructor(private val pixabayService: PixabayService) {

    fun getSearchResult(searchQuery: String): Observable<List<ImageListItemModel>> {
        val apiKey = BuildConfig.PIXABAY_KEY
        return pixabayService.getSearchResult(searchQuery, apiKey)
            .map {
                val result = mutableListOf<ImageListItemModel>()
                it.hits.forEach { hit ->
                    run {
                        result.add(
                            ImageListItemModel(
                                id = hit.id,
                                username = hit.user,
                                thumbnail = hit.previewURL,
                                tags = hit.tags
                            )
                        )
                    }
                }
                return@map result
            }
    }
}