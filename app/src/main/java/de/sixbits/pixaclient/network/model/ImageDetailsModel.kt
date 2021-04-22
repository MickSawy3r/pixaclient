package de.sixbits.pixaclient.network.model

class ImageDetailsModel constructor(
    private val image: String,
    private val username: String,
    private val tags: String,
    private val likes: Int,
    private val favorites: Int,
    private val comments: Int
) {

    fun getImage(): String {
        return image
    }

    fun getUsername(): String {
        return username
    }

    fun getTags(): String {
        return tags
    }

    fun getLikes(): Int {
        return likes
    }

    fun getFavorites(): Int {
        return favorites
    }

    fun getComments(): Int {
        return comments
    }
}