package com.example.movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.movieapp.data.datasource.local.room.MovieDao
import com.example.movieapp.data.datasource.remote.network.MovieApiService
import com.example.movieapp.data.mapper.toMovie
import com.example.movieapp.data.mapper.toMovieDetail
import com.example.movieapp.data.mapper.toMovieEntity
import com.example.movieapp.data.paging.MoviePagingSource
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MovieDetail
import com.example.movieapp.domain.repository.MovieRepository
import com.example.movieapp.utils.Constans.PAGE_SIZE
import com.example.movieapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService,
    private val movieDao: MovieDao,
) : MovieRepository {
    override fun getMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource(movieApiService, query)
            }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toMovie()
            }
        }
    }

    override fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> = flow {
        emit(Resource.Loading())
        try {
            val response = movieApiService.getMovieDetail(movieId = movieId)
            emit(Resource.Success(response.toMovieDetail()))
        } catch (e: Exception) {
            when(e) {
                is IOException -> {
                    emit(Resource.Error("No Internet Connection"))
                }
                else -> {
                    emit(Resource.Error("An unexpected error occurred"))
                }
            }
        }
    }

    override fun getMovieFavorites(): Flow<List<Movie>> = movieDao.getMovies()
        .map {
            it.map { movieEntity ->
                movieEntity.toMovie()
            }
        }

    override fun checkIsFavorite(movieId: Int): Flow<Boolean> = flow {
        val movieEntity = movieDao.getMovieById(movieId)
        emit(movieEntity != null)
    }

    override fun addToFavorite(movieDetail: MovieDetail): Flow<Boolean> = flow {
        movieDao.insertMovie(movieDetail.toMovieEntity())
        emit(true)
    }

    override fun removeFromFavorite(movieDetail: MovieDetail): Flow<Boolean> = flow {
        movieDao.deleteMovie(movieDetail.toMovieEntity())
        emit(false)
    }
}