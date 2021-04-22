package de.sixbits.pixaclient.network.model

data class ImageDetailsModel constructor(
    val image: String,
    val username: String,
    val tags: String,
    val likes: Int,
    val favorites: Int,
    val comments: Int
)