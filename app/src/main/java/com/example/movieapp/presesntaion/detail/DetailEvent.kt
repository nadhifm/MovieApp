package com.example.movieapp.presesntaion.detail

sealed class DetailEvent {
    object RefreshDetail: DetailEvent()
    object OnFavoriteChange: DetailEvent()
}