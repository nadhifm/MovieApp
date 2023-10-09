package com.example.movieapp.presesntaion.detail

import com.example.movieapp.domain.model.MovieDetail

data class DetailState(
    val movieId: Int = -1,
    val isLoading: Boolean = false,
    val movieDetail: MovieDetail = MovieDetail(
        -1,
        "",
        "",
        0.0,
        overview = "",
        backdropPath = "",
        genres = listOf()
    ),
    val isFavorite: Boolean = false,
    val isError: Boolean = false,
    val message: String = "",
)