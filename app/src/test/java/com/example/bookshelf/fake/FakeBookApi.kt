package com.example.bookshelf.fake

import com.example.bookshelf.model.Book
import com.example.bookshelf.model.MultiBook
import com.example.bookshelf.model.getDefaultBook
import com.example.bookshelf.network.BookApi

class FakeBookApi:BookApi {
    override suspend fun getBooks(query: String, projection: String, maxResults: Int): MultiBook {
        return FakeDataSource.multiBook
    }

    override suspend fun getBookById(volId: String): Book {
        return FakeDataSource.getBookById(volId)?: getDefaultBook()
    }
}