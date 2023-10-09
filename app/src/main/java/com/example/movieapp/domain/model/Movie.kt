package com.example.movieapp.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val voteAverage: Double,
    val backdropPath: String,
)
