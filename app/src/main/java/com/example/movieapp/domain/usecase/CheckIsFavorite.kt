package com.example.movieapp.domain.usecase

import com.example.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckIsFavorite @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun  invoke(movieId: Int): Flow<Boolean> {
        return movieRepository.checkIsFavorite(movieId)
    }
}