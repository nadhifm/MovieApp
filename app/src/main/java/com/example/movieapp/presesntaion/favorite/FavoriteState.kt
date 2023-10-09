package com.example.movieapp.presesntaion.favorite

import com.example.movieapp.domain.model.Movie

data class FavoriteState(
    val movies: List<Movie> = listOf()
)