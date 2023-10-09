package com.example.movieapp.presesntaion

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.navigation.Screen
import com.example.movieapp.presesntaion.detail.DetailScreen
import com.example.movieapp.presesntaion.favorite.FavoriteScreen
import com.example.movieapp.presesntaion.home.HomeScreen

@Composable
fun MovieApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier,
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                navigateToDetailScreen = {
                    navController.navigate(Screen.Detail.createRoute(it))
                },
                navigateToFavoriteScreen = {
                    navController.navigate(Screen.Favorite.route)
                }
            )
        }
        composable(
            Screen.Detail.route,
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.IntType
                    defaultValue = -1
                },
            ),
        ) {
            DetailScreen(
                navigateBack = {
                    navController.navigateUp()
                },
            )
        }
        composable(Screen.Favorite.route) {
            FavoriteScreen(
                navigateToDetailScreen = {
                    navController.navigate(Screen.Detail.createRoute(it))
                },
                navigateBack = {
                    navController.navigateUp()
                },
            )
        }
    }
}