package com.example.movieapp.domain.usecase

import com.example.movieapp.domain.model.MovieDetail
import com.example.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveFromFavorite @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(movieDetail: MovieDetail): Flow<Boolean> {
        return movieRepository.removeFromFavorite(movieDetail)
    }
}