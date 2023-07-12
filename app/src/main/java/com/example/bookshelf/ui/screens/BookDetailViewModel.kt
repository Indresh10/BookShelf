package com.example.bookshelf.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookApplication
import com.example.bookshelf.data.BookRepository
import com.example.bookshelf.model.Book
import kotlinx.coroutines.launch
import java.lang.Exception

sealed interface BookDetailUiState{
    data class Success(val book: Book):BookDetailUiState
    object Loading:BookDetailUiState
    object Error:BookDetailUiState
}
private const val TAG = "BookDetailViewModel"
class BookDetailViewModel(savedStateHandle: SavedStateHandle, private val repository: BookRepository):ViewModel() {
    var uiState:BookDetailUiState by mutableStateOf(BookDetailUiState.Loading)
        private set
    private val volId :String =  checkNotNull(savedStateHandle["bookId"])
    init {
        loadBook()
    }
    fun loadBook(){
        uiState = BookDetailUiState.Loading
        viewModelScope.launch {
            uiState = try {
                BookDetailUiState.Success(repository.getBookById(volId))
            }catch (e:Exception){
                Log.e(TAG,e.message,e.cause)
                BookDetailUiState.Error
            }
        }
    }

    companion object{
        val factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as BookApplication
                BookDetailViewModel(this.createSavedStateHandle(),application.appContainer.bookRepository)
            }
        }
    }
}