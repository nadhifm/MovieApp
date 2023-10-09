package com.example.movieapp.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val voteAverage: Double,
    val overview: String,
    val backdropPath: String,
    val genres: List<Genre>,
)
