package com.example.bookshelf.data

import com.example.bookshelf.model.Book
import com.example.bookshelf.model.MultiBook
import com.example.bookshelf.network.BookApi

interface BookRepository {
    suspend fun getBooks(query:String):MultiBook
    suspend fun getBookById(volId: String):Book
}

class NetworkBookRepository(private val bookApi: BookApi):BookRepository {
    override suspend fun getBooks(query: String): MultiBook = bookApi.getBooks(query)

    override suspend fun getBookById(volId: String): Book = bookApi.getBookById(volId)
}