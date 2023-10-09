package com.example.movieapp.data.datasource.remote.network

import com.example.movieapp.data.datasource.remote.response.GenreResponse
import com.example.movieapp.data.datasource.remote.response.GetMoviesResponse
import com.example.movieapp.data.datasource.remote.response.MovieDetailResponse
import com.example.movieapp.domain.usecase.GetMovieDetail
import com.example.movieapp.utils.Constans.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/popular")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): GetMoviesResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): GetMoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieDetailResponse
}