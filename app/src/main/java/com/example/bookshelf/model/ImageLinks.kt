package com.example.bookshelf.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageLinks(
    val smallThumbnail:String,
    val thumbnail:String
)

fun ImageLinks.getThumbnail():String = this.thumbnail.replace("http://","https://")
