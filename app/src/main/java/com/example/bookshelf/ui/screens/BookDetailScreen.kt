package com.example.bookshelf.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.model.Book
import com.example.bookshelf.model.getDefaultBook
import com.example.bookshelf.model.getThumbnail
import com.example.bookshelf.ui.common.ErrorScreen
import com.example.bookshelf.ui.common.LoadingScreen

@Composable
fun BookDetailScreen(
    uiState: BookDetailUiState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is BookDetailUiState.Error -> ErrorScreen(
            onRetry = onRetry,
            modifier = modifier.fillMaxSize()
        )

        is BookDetailUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is BookDetailUiState.Success -> BookDetailLayout(
            book = uiState.book,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun BookDetailLayout(book: Book, modifier: Modifier = Modifier) {
    val verticalScroll = rememberScrollState()
    Column(modifier = modifier.verticalScroll(verticalScroll)) {
        BookAndImageRow(book = book, modifier = Modifier.fillMaxWidth())
        HTMLText(
            text = book.volumeInfo.description,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 16.dp)
        )
    }
}

@Composable
fun BookAndImageRow(book: Book, modifier: Modifier = Modifier) {
    val ctx = LocalContext.current
    Row(modifier = modifier) {
        BookDetailsColumn(book = book, modifier = Modifier.weight(1f))
        AsyncImage(
            model = ImageRequest.Builder(ctx)
                .data(book.volumeInfo.imageLinks?.getThumbnail())
                .placeholder(R.drawable.baseline_downloading_24)
                .error(R.drawable.baseline_broken_image_24)
                .build(),
            contentDescription = book.volumeInfo.title,
            modifier = Modifier
                .width(128.dp)
                .heightIn(min = 100.dp, max = 300.dp)
                .shadow(4.dp, RoundedCornerShape(8.dp))
        )
    }
}

@Composable
fun BookDetailsColumn(book: Book, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        BookDetailText(
            key = "Title",
            value = book.volumeInfo.title,
            modifier = Modifier.padding(8.dp)
        )
        if (book.volumeInfo.subtitle != null)
            BookDetailText(
                key = "Subtitle",
                value = book.volumeInfo.subtitle,
                modifier = Modifier.padding(8.dp)
            )
        if (book.volumeInfo.publisher != null)
            BookDetailText(
                key = "Publisher",
                value = book.volumeInfo.publisher,
                modifier = Modifier.padding(8.dp)
            )
        if (book.volumeInfo.publishedDate != null)
            BookDetailText(
                key = "Published Date",
                value = book.volumeInfo.publishedDate,
                modifier = Modifier.padding(8.dp)
            )
        if (book.volumeInfo.authors.isNotEmpty())
            Text(
                text = "by ${book.volumeInfo.authors.joinToString()}",
                textAlign = TextAlign.End,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
    }
}

@Composable
fun BookDetailText(key: String, value: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Text(text = "$key: ", fontWeight = FontWeight.Bold)
        Text(text = value)
    }
}

@Preview
@Composable
fun BookDetailPreview() {
    BookDetailLayout(book = getDefaultBook())
}

@SuppressLint("WrongConstant")
@Composable
fun HTMLText(text: String, modifier: Modifier = Modifier) {
    val htmlText = "<div>$text</div>"
    val html = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT)
    val txtColor = MaterialTheme.colorScheme.onBackground
    val fontSize = MaterialTheme.typography.bodyLarge.fontSize
    AndroidView(modifier = modifier, factory = {
        TextView(it).apply {
            setText(html)
            setTextColor(txtColor.toArgb())
            textSize = fontSize.value
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                justificationMode = JUSTIFICATION_MODE_INTER_WORD
            }
        }
    })
}