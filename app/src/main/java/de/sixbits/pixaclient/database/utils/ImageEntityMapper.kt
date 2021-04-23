package de.sixbits.pixaclient.database.utils

import de.sixbits.pixaclient.database.entities.ImageEntity
import de.sixbits.pixaclient.network.model.ImageDetailsModel
import java.util.*

object ImageEntityMapper {
    fun fromImageDetailsModel(model: ImageDetailsModel): ImageEntity {
        return ImageEntity(
            id = model.id,
            image = model.image,
            username = model.username,
            tags = model.tags,
            favorites = model.favorites,
            comments = model.comments,
            likes = model.likes,
            createdAt = Calendar.getInstance().time
        )
    }
}