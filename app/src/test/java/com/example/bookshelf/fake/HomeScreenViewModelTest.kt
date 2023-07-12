package com.example.bookshelf.fake

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.createSavedStateHandle
import com.example.bookshelf.rule.TestDispatcherRule
import com.example.bookshelf.ui.screens.BookDetailUiState
import com.example.bookshelf.ui.screens.BookDetailViewModel
import com.example.bookshelf.ui.screens.BookUiState
import com.example.bookshelf.ui.screens.HomeScreenViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


class FakeBookRepositoryTest() {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun getMultiBook() = runTest {
        val repository = FakeBookDataRepository()
        Assert.assertEquals(FakeDataSource.multiBook, repository.getBooks(""))
    }

    @Test
    fun getBookFromRepository_Through_its_ID() = runTest {
        val repository = FakeBookDataRepository()
        Assert.assertEquals(FakeDataSource.getBookById("book1"), repository.getBookById("book1"))
    }

    @Test
    fun testHomeScreenViewModel() = runTest {
        val viewModel = HomeScreenViewModel(FakeBookDataRepository())
        viewModel.updateQuery("Hi")
        Assert.assertEquals(viewModel.query, "Hi")
        viewModel.getBooks()
        Assert.assertEquals(BookUiState.Success(FakeDataSource.books), viewModel.bookUiState)
    }

    @Test
    fun testBookDetailViewModel() = runTest {
        val viewModel = BookDetailViewModel(SavedStateHandle(mapOf("bookId" to "book1")),FakeBookDataRepository())
        Assert.assertEquals(BookDetailUiState.Success(FakeDataSource.getBookById("book1")!!),viewModel.uiState)
    }
}