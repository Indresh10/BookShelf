package com.example.bookshelf.data

import com.example.bookshelf.network.BookApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val bookRepository:BookRepository
}

class AppNetworkContainer:AppContainer {

    private val baseUrl = "https://www.googleapis.com/books/v1/"

    override val bookRepository: BookRepository by lazy {
        NetworkBookRepository(retrofitService)
    }
    private val json = Json { ignoreUnknownKeys = true }
    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val retrofitService by lazy {
        retrofit.create(BookApi::class.java)
    }
}