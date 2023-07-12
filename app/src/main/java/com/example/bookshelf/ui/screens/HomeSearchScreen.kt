package com.example.bookshelf.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.model.Book
import com.example.bookshelf.model.getDefaultBook
import com.example.bookshelf.model.getThumbnail
import com.example.bookshelf.ui.common.ErrorScreen
import com.example.bookshelf.ui.common.InitialScreen
import com.example.bookshelf.ui.common.LoadingScreen

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    onBookClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.bookUiState
    Column(modifier = modifier) {
        SearchBox(
            query = viewModel.query,
            onValueChange = {
                viewModel.updateQuery(it)
            }, onSearch = {
                viewModel.getBooks()
            })
        when (uiState) {
            is BookUiState.Initial -> InitialScreen(modifier = Modifier.fillMaxSize())
            is BookUiState.Error -> ErrorScreen(
                onRetry = { viewModel.getBooks() },
                modifier = Modifier.fillMaxSize()
            )

            is BookUiState.Success -> BookImageGrid(
                books = uiState.books,
                onBookClick = onBookClick,
                modifier = Modifier.fillMaxSize()
            )

            is BookUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBox(
    query: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = modifier.padding(8.dp),
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(4.dp)
    ) {
        TextField(
            value = query,
            onValueChange = onValueChange,
            trailingIcon = {
                IconButton(onClick = {
                    onSearch()
                    keyboardController?.hide()
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }
            },
            placeholder = {
                Text(text = stringResource(id = R.string.search))
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearch()
                keyboardController?.hide()
            }),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp)
                .background(MaterialTheme.colorScheme.onSecondaryContainer)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    SearchBox(query = "", onValueChange = {}, onSearch = {})
}

@Composable
fun BookImageGrid(books: List<Book>, onBookClick: (String) -> Unit, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(books) {
            BookImageItem(book = it, onBookClick = onBookClick)
        }
    }
}

@Composable
fun BookImageItem(book: Book, onBookClick: (String) -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(book.volumeInfo.imageLinks?.getThumbnail())
            .placeholder(R.drawable.baseline_downloading_24)
            .error(R.drawable.baseline_broken_image_24)
            .build(),
        contentDescription = book.volumeInfo.title,
        modifier = modifier
            .clickable {
                onBookClick(book.id)
            }
            .fillMaxWidth()
            .heightIn(max = 300.dp)
            .background(Color.White)
    )
}

@Preview
@Composable
fun BookGridPreview() {
    val mockList = List(10) { getDefaultBook() }
    BookImageGrid(books = mockList, onBookClick = {})
}
