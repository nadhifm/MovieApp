package com.example.movieapp.utils

import com.example.movieapp.data.datasource.local.entity.MovieEntity
import com.example.movieapp.data.datasource.remote.response.GenreResponse
import com.example.movieapp.data.datasource.remote.response.GetMoviesResponse
import com.example.movieapp.data.datasource.remote.response.MovieDetailResponse
import com.example.movieapp.data.datasource.remote.response.MovieResponse
import com.example.movieapp.domain.model.Genre
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MovieDetail
import java.io.IOException

object DataDummy {
    fun generateListMovie(): List<Movie> {
        return listOf(
            Movie(
                id = 968051,
                title = "The Nun II",
                releaseDate = "2023-09-06",
                voteAverage = 7.0,
                backdropPath = "/5gzzkR7y3hnY8AD1wXjCnVlHba5.jpg"
            ),
            Movie(
                id = 678512,
                title = "Sound of Freedom",
                releaseDate = "2023-07-03",
                voteAverage = 8.1,
                backdropPath = "/qA5kPYZA7FkVvqcEfJRoOy4kpHg.jpg"
            ),
            Movie(
                id = 980489,
                title = "Gran Turismo",
                releaseDate = "2023-08-09",
                voteAverage = 8.0,
                backdropPath = "/51tqzRtKMMZEYUpSYkrUE7v9ehm.jpg"
            ),
            Movie(
                id = 565770,
                title = "Blue Beetle",
                releaseDate = "2023-08-16",
                voteAverage = 7.1,
                backdropPath = "/mXLOHHc1Zeuwsl4xYKjKh2280oL.jpg"
            ),
            Movie(
                id = 926393,
                title = "The Equalizer 3",
                releaseDate = "2023-08-30",
                voteAverage = 7.2,
                backdropPath = "/b0Ej6fnXAP8fK75hlyi2jKqdhHz.jpg"
            ),
        )
    }

    fun generateMovieDetail(): MovieDetail {
        return MovieDetail(
            id = 968051,
            title = "The Nun II",
            releaseDate = "2023-09-06",
            voteAverage = 7.0,
            overview = "In 1956 France, a priest is violently murdered, and Sister Irene begins to investigate. She once again comes face-to-face with a powerful evil.",
            backdropPath = "/mRGmNnh6pBAGGp6fMBMwI8iTBUO.jpg",
            genres = listOf(
                Genre(
                    id = 27,
                    name = "Horror"
                ),
                Genre(
                    id = 9648,
                    name = "Mystery"
                ),
                Genre(
                    id = 53,
                    name = "Thriller"
                ),
            )
        )
    }

    fun generateErrorMessage(): String {
        return "An unexpected error occurred"
    }

    fun generateIOException(): Exception {
        return IOException("No Internet Connection")
    }

    fun generateGetMovieResponse(): GetMoviesResponse {
        return GetMoviesResponse(
            page = 1,
            movieResponses = listOf(
                MovieResponse(
                    adult = false,
                    backdropPath = "/mRGmNnh6pBAGGp6fMBMwI8iTBUO.jpg",
                    genreIds = listOf(
                        27,
                        9648,
                        53
                    ),
                    id = 968051,
                    originalLanguage = "en",
                    originalTitle = "The Nun II",
                    overview = "In 1956 France, a priest is violently murdered, and Sister Irene begins to investigate. She once again comes face-to-face with a powerful evil.",
                    popularity = 5340.847,
                    posterPath = "/5gzzkR7y3hnY8AD1wXjCnVlHba5.jpg",
                    releaseDate = "2023-09-06",
                    title = "The Nun II",
                    video = false,
                    voteAverage = 7.0,
                    voteCount = 653
                ),
                MovieResponse(
                    adult = false,
                    backdropPath = "/pA3vdhadJPxF5GA1uo8OPTiNQDT.jpg",
                    genreIds = listOf(
                        28,
                        18
                    ),
                    id = 678512,
                    originalTitle =  "en",
                    originalLanguage =  "Sound of Freedom",
                    overview =  "The story of Tim Ballard, a former US government agent, who quits his job in order to devote his life to rescuing children from global sex traffickers.",
                    popularity =  3187.754,
                    posterPath =  "/qA5kPYZA7FkVvqcEfJRoOy4kpHg.jpg",
                    releaseDate =  "2023-07-03",
                    title =  "Sound of Freedom",
                    video =  false,
                    voteAverage =  8.1,
                    voteCount = 710
                ),
                MovieResponse(
                    adult = false,
                    backdropPath =  "/dWnABFqQN6Hu8eIIiFAMil7lUXO.jpg",
                    genreIds = listOf(
                        12,
                        28,
                        18
                    ),
                    id =  980489,
                    originalLanguage =  "en",
                    originalTitle =  "Gran Turismo",
                    overview =  "The ultimate wish-fulfillment tale of a teenage Gran Turismo player whose gaming skills won him a series of Nissan competitions to become an actual professional racecar driver.",
                    popularity =  2320.595,
                    posterPath =  "/51tqzRtKMMZEYUpSYkrUE7v9ehm.jpg",
                    releaseDate =  "2023-08-09",
                    title =  "Gran Turismo",
                    video = false,
                    voteAverage =  8.0,
                    voteCount = 882
                )
            ),
            totalPages = 40400,
            totalResults = 807997
        )
    }

    fun generateMovieDetailResponse(): MovieDetailResponse {
        return MovieDetailResponse(
            adult =  false,
            backdropPath =  "/mRGmNnh6pBAGGp6fMBMwI8iTBUO.jpg",
            budget =  38500000,
            homepage =  "https://www.warnerbros.com/movies/nun2",
            id =  968051,
            imdbId =  "tt10160976",
            originalLanguage =  "en",
            originalTitle =  "The Nun II",
            overview =  "In 1956 France, a priest is violently murdered, and Sister Irene begins to investigate. She once again comes face-to-face with a powerful evil.",
            popularity =  5340.847,
            posterPath =  "/5gzzkR7y3hnY8AD1wXjCnVlHba5.jpg",
            releaseDate =  "2023-09-06",
            revenue =  235010000,
            runtime =  110,
            status =  "Released",
            tagline =  "Confess your sins.",
            title =  "The Nun II",
            video =  false,
            voteAverage =  7.025,
            voteCount =  653,
            genreResponses = listOf(
                GenreResponse(
                    id = 27,
                    name = "Horror"
                ),
                GenreResponse(
                    id = 9648,
                    name = "Mystery"
                ),
                GenreResponse(
                    id = 53,
                    name = "Thriller"
                ),
            )
        )
    }

    fun generateListMovieEntity(): List<MovieEntity> {
        return listOf(
            MovieEntity(
                id = 968051,
                title = "The Nun II",
                releaseDate = "2023-09-06",
                voteAverage = 7.0,
                backdropPath = "/5gzzkR7y3hnY8AD1wXjCnVlHba5.jpg"
            ),
            MovieEntity(
                id = 678512,
                title = "Sound of Freedom",
                releaseDate = "2023-07-03",
                voteAverage = 8.1,
                backdropPath = "/qA5kPYZA7FkVvqcEfJRoOy4kpHg.jpg"
            ),
            MovieEntity(
                id = 980489,
                title = "Gran Turismo",
                releaseDate = "2023-08-09",
                voteAverage = 8.0,
                backdropPath = "/51tqzRtKMMZEYUpSYkrUE7v9ehm.jpg"
            ),
            MovieEntity(
                id = 565770,
                title = "Blue Beetle",
                releaseDate = "2023-08-16",
                voteAverage = 7.1,
                backdropPath = "/mXLOHHc1Zeuwsl4xYKjKh2280oL.jpg"
            ),
            MovieEntity(
                id = 926393,
                title = "The Equalizer 3",
                releaseDate = "2023-08-30",
                voteAverage = 7.2,
                backdropPath = "/b0Ej6fnXAP8fK75hlyi2jKqdhHz.jpg"
            ),
        )
    }

    fun generateMovieEntity(): MovieEntity {
        return MovieEntity(
            id = 968051,
            title = "The Nun II",
            releaseDate = "2023-09-06",
            voteAverage = 7.0,
            backdropPath = "/5gzzkR7y3hnY8AD1wXjCnVlHba5.jpg"
        )
    }
}