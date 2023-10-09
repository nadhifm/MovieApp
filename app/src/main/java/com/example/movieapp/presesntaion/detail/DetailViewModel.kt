package com.example.movieapp.presesntaion.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.MovieDetail
import com.example.movieapp.domain.usecase.AddToFavorite
import com.example.movieapp.domain.usecase.CheckIsFavorite
import com.example.movieapp.domain.usecase.GetMovieDetail
import com.example.movieapp.domain.usecase.RemoveFromFavorite
import com.example.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetail: GetMovieDetail,
    private val checkIsFavorite: CheckIsFavorite,
    private val addToFavorite: AddToFavorite,
    private val removeFromFavorite: RemoveFromFavorite,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(DetailState())

    init {
        savedStateHandle.get<Int>("movieId")?.let { movieId ->
            if (movieId != -1) {
                state = state.copy(
                    movieId = movieId
                )
                checkFavorite()
                getDetail()
            }
        }
    }

    fun onEvent(event: DetailEvent) {
        when(event) {
            is DetailEvent.RefreshDetail -> {
                getDetail()
            }
            is DetailEvent.OnFavoriteChange -> {
                if (state.isFavorite) {
                    deleteFromFavorite()
                } else {
                    saveToFavorite()
                }
            }
        }
    }

    private fun checkFavorite(id: Int = state.movieId) {
        viewModelScope.launch {
            checkIsFavorite(id).collect {
                state = state.copy(
                    isFavorite = it
                )
            }
        }
    }

    private fun saveToFavorite(movieDetail: MovieDetail = state.movieDetail) {
        viewModelScope.launch {
            addToFavorite(movieDetail).collect {
                state = state.copy(
                    isFavorite = it
                )
            }
        }
    }

    private fun deleteFromFavorite(movieDetail: MovieDetail = state.movieDetail) {
        viewModelScope.launch {
            removeFromFavorite(movieDetail).collect {
                state = state.copy(
                    isFavorite = it
                )
            }
        }
    }

    private fun getDetail(id: Int = state.movieId) {
        viewModelScope.launch {
            getMovieDetail(id)
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { movieDetail ->
                                state = state.copy(
                                    isError = false,
                                    isLoading = false,
                                    movieDetail = movieDetail,
                                    message = ""
                                )
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                isLoading = false,
                                isError = true,
                                message = result.message ?: "An unexpected error occurred"
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                isError = false,
                                isLoading = result.isLoading,
                                message = ""
                            )
                        }
                    }
                }
        }
    }
}