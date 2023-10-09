package com.example.movieapp.presesntaion.favorite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.usecase.GetMovieFavorites
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getMovieFavorites: GetMovieFavorites
) : ViewModel() {
    var state by mutableStateOf(FavoriteState())

    init {
        getFavorites()
    }

    private fun getFavorites() {
        viewModelScope.launch {
            getMovieFavorites.invoke()
                .collect { result ->
                    state = state.copy(
                        movies = result
                    )
                }
        }
    }
}