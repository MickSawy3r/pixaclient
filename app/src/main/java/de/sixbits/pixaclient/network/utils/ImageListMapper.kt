package de.sixbits.pixaclient.network.utils

import de.sixbits.pixaclient.database.entities.ImageEntity
import de.sixbits.pixaclient.network.model.ImageListItemModel
import de.sixbits.pixaclient.network.response.PixabaySearchResponse

object ImageListMapper {
    fun fromImageListItemModel(hit: PixabaySearchResponse.Hit): ImageListItemModel {
        return ImageListItemModel(
            id = hit.id,
            username = hit.user,
            thumbnail = hit.previewURL,
            tags = hit.tags
        )
    }

    fun fromImageEntity(imageEntity: ImageEntity): ImageListItemModel {
        return ImageListItemModel(
            id = imageEntity.id,
            username = imageEntity.username,
            thumbnail = imageEntity.image,
            tags = imageEntity.tags
        )
    }
}