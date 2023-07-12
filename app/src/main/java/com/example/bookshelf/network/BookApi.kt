package com.example.bookshelf.network

import com.example.bookshelf.model.Book
import com.example.bookshelf.model.MultiBook
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface BookApi {
    @GET("volumes")
    suspend fun getBooks(@Query("q") query:String,@Query("projection") projection:String = "lite",@Query("maxResults") maxResults:Int = 40) : MultiBook

    @GET("volumes/{volId}")
    suspend fun getBookById(@Path("volId") volId:String) : Book
}