package com.example.bookshelf.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookApplication
import com.example.bookshelf.data.BookRepository
import com.example.bookshelf.model.Book
import kotlinx.coroutines.launch

sealed interface BookUiState {
    data class Success(val books : List<Book>):BookUiState
    object Loading:BookUiState
    object Error:BookUiState
    object Initial:BookUiState
}

private const val TAG = "HomeScreenViewModel"

class HomeScreenViewModel(private val bookRepository: BookRepository):ViewModel() {
    var bookUiState:BookUiState by mutableStateOf(BookUiState.Initial)
        private set

    var query by mutableStateOf("")
        private set

    fun getBooks() {
        viewModelScope.launch {
            bookUiState = BookUiState.Loading
            bookUiState = try {
                if(query.isNotBlank())
                    BookUiState.Success(
                        bookRepository.getBooks(query).items
                    )
                else
                    BookUiState.Initial
            }catch (e:Exception){
                Log.e(TAG,"Can't get Data",e.cause)
                BookUiState.Error
            }
        }
    }

    fun updateQuery(word:String){
        query = word
    }

    companion object{
        val factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as BookApplication
                HomeScreenViewModel(application.appContainer.bookRepository)
            }
        }
    }
}