package com.example.movieapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapp.data.datasource.remote.network.MovieApiService
import com.example.movieapp.data.datasource.remote.response.MovieResponse
import java.io.IOException
import javax.inject.Inject

class MoviePagingSource(
    private val movieApiService: MovieApiService,
    private val query: String,
) : PagingSource<Int, MovieResponse>() {
    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        val pageIndex = params.key ?: 1
        return try {
            val apiResponse = if (query == "") {
                movieApiService.getMovies(pageIndex)
            } else {
                movieApiService.searchMovies(
                    query = query,
                    page = pageIndex
                )
            }
            val movieResponses = apiResponse.movieResponses
            LoadResult.Page(
                data = movieResponses,
                prevKey = if (pageIndex == 1) null else pageIndex,
                nextKey = if (movieResponses.isEmpty()) null else pageIndex + 1
            )
        } catch (e: Exception) {
            when(e) {
                is IOException -> {
                    return LoadResult.Error(Exception("No Internet Connection"))
                }
                else -> {
                    return LoadResult.Error(Exception("An unexpected error occurred"))
                }
            }
        }
    }
}