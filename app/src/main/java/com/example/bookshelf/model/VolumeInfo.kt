package com.example.bookshelf.model

import kotlinx.serialization.Serializable

@Serializable
data class VolumeInfo(
    val title:String,
    val subtitle:String? = null,
    val authors:List<String> = emptyList(),
    val publisher:String? = null,
    val publishedDate:String? = null,
    val description:String = "No Description",
    val imageLinks:ImageLinks? = null
)
