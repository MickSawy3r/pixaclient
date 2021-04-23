package de.sixbits.pixaclient.network.utils

import de.sixbits.pixaclient.database.entities.ImageEntity
import de.sixbits.pixaclient.network.model.ImageDetailsModel
import de.sixbits.pixaclient.network.model.ImageListItemModel
import de.sixbits.pixaclient.network.response.PixabaySearchResponse

object ImageDetailsMapper {
    fun fromImageListItemModel(it: PixabaySearchResponse.Hit): ImageDetailsModel {
        return ImageDetailsModel(
            id = it.id,
            image = it.largeImageURL,
            username = it.user,
            tags = it.tags,
            comments = it.comments,
            favorites = it.favorites,
            likes = it.likes
        )
    }

    fun fromImageEntity(it: ImageEntity): ImageDetailsModel {
        return ImageDetailsModel(
            id = it.id,
            image = it.image,
            username = it.username,
            tags = it.tags,
            comments = it.comments,
            favorites = it.favorites,
            likes = it.likes
        )
    }
}