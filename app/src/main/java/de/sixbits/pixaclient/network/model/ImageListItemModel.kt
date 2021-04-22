package de.sixbits.pixaclient.network.model

class ImageListItemModel constructor(
    private val id: Int,
    private val thumbnail: String,
    private val username: String,
    private val tags: String
) {

    fun getId(): Int {
        return id
    }

    fun getThumbnail(): String {
        return thumbnail
    }

    fun getUsername(): String {
        return username
    }

    fun getTags(): String {
        return tags
    }
}