package com.example.movieapp.data.mapper

import com.example.movieapp.data.datasource.local.entity.MovieEntity
import com.example.movieapp.data.datasource.remote.response.GenreResponse
import com.example.movieapp.data.datasource.remote.response.MovieDetailResponse
import com.example.movieapp.data.datasource.remote.response.MovieResponse
import com.example.movieapp.domain.model.Genre
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MovieDetail

fun MovieResponse.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        backdropPath = if (backdropPath != null) "https://image.tmdb.org/t/p/w500/$backdropPath" else "",
    )
}

fun MovieDetailResponse.toMovieDetail(): MovieDetail {
    return MovieDetail(
        id = id,
        title = title,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        overview = overview,
        backdropPath = if (backdropPath != null) "https://image.tmdb.org/t/p/w500/$backdropPath" else "",
        genres = genreResponses.map {
            it.toGenre()
        }
    )
}

private fun GenreResponse.toGenre(): Genre {
    return Genre(
        id = id,
        name = name
    )
}

fun MovieDetail.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        backdropPath = backdropPath,
    )
}

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        backdropPath = if (backdropPath != "") "https://image.tmdb.org/t/p/w500/$backdropPath" else "",
    )
}
