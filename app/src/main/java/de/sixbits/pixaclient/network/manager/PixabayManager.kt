package de.sixbits.pixaclient.network.manager

import de.sixbits.pixaclient.BuildConfig
import de.sixbits.pixaclient.network.model.ImageDetailsModel
import de.sixbits.pixaclient.network.model.ImageListItemModel
import de.sixbits.pixaclient.network.service.PixabayService
import io.reactivex.Observable
import javax.inject.Inject

class PixabayManager @Inject constructor(private val pixabayService: PixabayService) {

    fun getSearchResult(searchQuery: String): Observable<List<ImageListItemModel>> {
        return pixabayService.getSearchResult(searchQuery, BuildConfig.PIXABAY_KEY)
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

    fun getImageDetails(imageId: Int): Observable<ImageDetailsModel> {
        return pixabayService.getImageDetails(imageId, BuildConfig.PIXABAY_KEY)
            .map {
                return@map ImageDetailsModel(
                    image = it.hits[0].largeImageURL,
                    username = it.hits[0].user,
                    tags = it.hits[0].tags,
                    comments = it.hits[0].comments,
                    favorites = it.hits[0].favorites,
                    likes = it.hits[0].likes
                )
            }
    }
}