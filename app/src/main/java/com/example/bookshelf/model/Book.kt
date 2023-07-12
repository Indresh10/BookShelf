package com.example.bookshelf.model

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val kind: String,
    val id: String,
    val volumeInfo: VolumeInfo,
)

fun getDefaultBook(): Book = Book(
    "book", "xyz", VolumeInfo(
        "", "", listOf("Hello","World"), "", "", "",
        ImageLinks("", "")
    )
)