package com.example.movieapp.domain.usecase

import com.example.movieapp.domain.model.MovieDetail
import com.example.movieapp.domain.repository.MovieRepository
import com.example.movieapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetail @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<Resource<MovieDetail>> {
        return movieRepository.getMovieDetail(movieId)
    }
}