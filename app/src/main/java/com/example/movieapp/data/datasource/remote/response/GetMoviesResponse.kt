package com.example.movieapp.data.datasource.remote.response


import com.google.gson.annotations.SerializedName

data class GetMoviesResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movieResponses: List<MovieResponse>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)