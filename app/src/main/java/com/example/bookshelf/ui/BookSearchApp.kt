package com.example.bookshelf.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bookshelf.R
import com.example.bookshelf.ui.screens.BookDetailScreen
import com.example.bookshelf.ui.screens.BookDetailViewModel
import com.example.bookshelf.ui.screens.HomeScreen
import com.example.bookshelf.ui.screens.HomeScreenViewModel

enum class BookScreen {
    BookList,
    BookDetail
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSearchApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()

    Scaffold(topBar = {
        BookTopBar(
            visible = BookScreen.valueOf(
                backStackEntry.value?.destination?.route?.split("/")?.get(0) ?: BookScreen.BookList.name
            ) != BookScreen.BookList,
            canNavigateBack = navController.previousBackStackEntry != null,
            onNavigateBack = { navController.navigateUp() },
            title = stringResource(id = R.string.book_details)
        )
    }) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BookScreen.BookList.name,
            modifier = modifier.padding(paddingValues)
        ) {
            composable(BookScreen.BookList.name) {
                HomeScreen(
                    viewModel = viewModel(factory = HomeScreenViewModel.factory),
                    onBookClick = {
                        navController.navigate("${BookScreen.BookDetail.name}/$it")
                    })
            }
            val bookIdArgument = "bookId"
            composable(
                route = BookScreen.BookDetail.name + "/{$bookIdArgument}",
                arguments = listOf(navArgument(bookIdArgument) { type = NavType.StringType })
            ) {
                val viewModel : BookDetailViewModel = viewModel(factory = BookDetailViewModel.factory)
                BookDetailScreen(uiState = viewModel.uiState, onRetry = viewModel::loadBook, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookTopBar(
    visible: Boolean,
    canNavigateBack: Boolean,
    onNavigateBack: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = modifier,
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_button),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        )
    }
}

