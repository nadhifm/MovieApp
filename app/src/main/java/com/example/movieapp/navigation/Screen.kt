package com.example.movieapp.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail?movieId={movieId}")  {
        fun createRoute(movieId: Int) = "detail?movieId=$movieId"
    }
    object Favorite : Screen("favorite")
}