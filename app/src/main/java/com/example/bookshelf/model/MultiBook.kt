package com.example.bookshelf.model

import kotlinx.serialization.Serializable

@Serializable
data class MultiBook(
    val kind:String,
    val totalItems:Int,
    val items:List<Book>
)
