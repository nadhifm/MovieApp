package com.example.movieapp.presesntaion.home

sealed class HomeEvent {
    data class OnSearchQueryChange(val query: String): HomeEvent()
}