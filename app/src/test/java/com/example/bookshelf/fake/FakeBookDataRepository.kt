package com.example.bookshelf.fake

import com.example.bookshelf.data.BookRepository
import com.example.bookshelf.model.Book
import com.example.bookshelf.model.MultiBook
import com.example.bookshelf.model.getDefaultBook

class FakeBookDataRepository:BookRepository {
    override suspend fun getBooks(query: String): MultiBook {
        return FakeDataSource.multiBook
    }

    override suspend fun getBookById(volId: String): Book {
        return FakeDataSource.getBookById(volId)?: getDefaultBook()
    }
}