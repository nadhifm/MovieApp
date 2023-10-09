package com.example.movieapp.presesntaion.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.movieapp.presesntaion.component.MovieItem
import com.example.movieapp.presesntaion.component.PaggingError
import com.example.movieapp.presesntaion.ui.theme.interFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToDetailScreen: (Int) -> Unit,
    navigateToFavoriteScreen: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    val movies = viewModel.state.movies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Movie App",
                        fontFamily = interFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF1B367B),
                ),
                actions = {
                    IconButton(onClick = navigateToFavoriteScreen) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Favorite",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Card(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(16.dp),
                ) {
                    TextField(
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        maxLines = 1,
                        value = state.query,
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = interFamily,
                        ),
                        placeholder = {
                            Text(
                                text = "Search",
                                fontFamily = interFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = Color.Gray,
                            )
                        },
                        onValueChange = { value ->
                            viewModel.onEvent(HomeEvent.OnSearchQueryChange(value))
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = "Search",
                                tint = Color.Gray
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            disabledTextColor = Color.Transparent,
                            containerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )
                }
            }

            when (movies.loadState.refresh) {
                is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    }
                }

                is LoadState.Error -> {
                    item {
                        PaggingError(
                            message = (movies.loadState.refresh as LoadState.Error).error.message
                                ?: "An unexpected error occurred",
                            onRetry = {
                                movies.retry()
                            },
                        )
                    }
                }

                else -> {
                    items(movies) { movie ->
                        if (movie != null) {
                            MovieItem(
                                movie = movie,
                                onClick = navigateToDetailScreen,
                            )
                        }
                    }
                    item {
                        if (movies.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(16.dp)
                            )
                        } else if (movies.loadState.append is LoadState.Error) {
                            PaggingError(
                                message = (movies.loadState.append as LoadState.Error).error.message
                                    ?: "An unexpected error occurred",
                                onRetry = {
                                    movies.retry()
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}