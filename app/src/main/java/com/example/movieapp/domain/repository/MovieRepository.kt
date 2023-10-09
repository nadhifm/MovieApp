package com.example.movieapp.domain.repository

import androidx.paging.PagingData
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MovieDetail
import com.example.movieapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(query: String): Flow<PagingData<Movie>>
    fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>>
    fun getMovieFavorites(): Flow<List<Movie>>
    fun checkIsFavorite(movieId: Int): Flow<Boolean>
    fun addToFavorite(movieDetail: MovieDetail): Flow<Boolean>
    fun removeFromFavorite(movieDetail: MovieDetail): Flow<Boolean>
}