package com.example.bookshelf.fake

import com.example.bookshelf.model.Book
import com.example.bookshelf.model.MultiBook
import com.example.bookshelf.model.VolumeInfo

object FakeDataSource {
    val books = List(10) {
        Book(
            "book", "book$it", VolumeInfo(
                "title$it"
            )
        )
    }
    val multiBook = MultiBook("book", 500, books)

    fun getBookById(id:String):Book?{
        for (book in books){
            if(book.id == id) return book
        }
        return null
    }

}