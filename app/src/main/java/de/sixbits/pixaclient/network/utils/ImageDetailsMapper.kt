package de.sixbits.pixaclient.network.utils

import de.sixbits.pixaclient.network.model.ImageDetailsModel
import de.sixbits.pixaclient.network.model.ImageListItemModel
import de.sixbits.pixaclient.network.response.PixabaySearchResponse

object ImageDetailsMapper {
    fun fromImageListItemModel(it: PixabaySearchResponse.Hit): ImageDetailsModel {
        return ImageDetailsModel(
            image = it.largeImageURL,
            username = it.user,
            tags = it.tags,
            comments = it.comments,
            favorites = it.favorites,
            likes = it.likes
        )
    }
}