/**
 * Manages the API service, this class provide the operations on the observables before they
 * get subscribed to by the ViewModel layer.
 * Any Mapping, Filtering ... operations on the RxObservables should happen here
 */

package de.sixbits.pixaclient.network.manager

import de.sixbits.pixaclient.BuildConfig
import de.sixbits.pixaclient.network.model.ImageDetailsModel
import de.sixbits.pixaclient.network.model.ImageListItemModel
import de.sixbits.pixaclient.network.service.PixabayService
import de.sixbits.pixaclient.network.utils.ImageDetailsMapper
import de.sixbits.pixaclient.network.utils.ImageListMapper
import io.reactivex.Observable
import javax.inject.Inject

open class PixabayManager @Inject constructor(private val pixabayService: PixabayService) {

    /**
     * Mapping Search result into consumables by the UI. No over-fetching should happen when it
     * comes to the view layer.
     */
    fun getSearchResult(searchQuery: String): Observable<List<ImageListItemModel>> {
        // The PIXABAY_KEY is provided in the sent email via local.properties file
        // since it should not be leaked. For more info view the README.md
        return pixabayService.getSearchResult(searchQuery, BuildConfig.PIXABAY_KEY)
            .map {
                val result = mutableListOf<ImageListItemModel>()
                it.hits.forEach { hit ->
                    run {
                        result.add(ImageListMapper.fromImageListItemModel(hit))
                    }
                }
                return@map result
            }
    }

    /**
     * Map response object to Image Details Model object when we get it
     */
    fun getImageDetails(imageId: Int): Observable<ImageDetailsModel> {
        // The PIXABAY_KEY is provided in the sent email via local.properties file
        // since it should not be leaked. For more info view the README.md
        return pixabayService.getImageDetails(imageId, BuildConfig.PIXABAY_KEY)
            .map {
                return@map ImageDetailsMapper.fromImageListItemModel(it.hits[0])
            }
    }
}
