package de.sixbits.pixaclient.network.model

data class ImageListItemModel constructor(
    val id: Int,
    val thumbnail: String,
    val username: String,
    val tags: String
)