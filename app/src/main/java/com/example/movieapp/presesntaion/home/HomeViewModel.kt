package com.example.movieapp.presesntaion.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.movieapp.domain.usecase.GetMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMovies: GetMovies,
) : ViewModel() {
    var state by mutableStateOf(HomeState())

    private var searchJob: Job? = null

    init {
        onEvent(HomeEvent.OnSearchQueryChange(""))
    }

    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.OnSearchQueryChange -> {
                state = state.copy(query = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getPagingData()
                }
            }
        }
    }

    private suspend fun getPagingData(query: String = state.query) {
        getMovies(query)
            .cachedIn(viewModelScope)
            .collect {
                state.movies.value = it
            }
    }
}