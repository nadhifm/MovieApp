package com.example.movieapp.presesntaion.home

import androidx.paging.PagingData
import com.example.movieapp.domain.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow

data class HomeState(
    val movies: MutableStateFlow<PagingData<Movie>> = MutableStateFlow(PagingData.empty()),
    val query: String = "",
)