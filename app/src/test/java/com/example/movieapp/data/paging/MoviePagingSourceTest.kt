package com.example.movieapp.data.paging

import androidx.paging.PagingSource
import com.example.movieapp.data.datasource.remote.network.MovieApiService
import com.example.movieapp.data.datasource.remote.response.MovieResponse
import com.example.movieapp.utils.DataDummy
import com.example.movieapp.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MoviePagingSourceTest {
    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineRule()

    @Mock
    private lateinit var movieApiService: MovieApiService

    private lateinit var moviePagingSource: MoviePagingSource

    @Test
    fun `When Query Empty Should Return List Movie`() = runTest {
        moviePagingSource = MoviePagingSource(movieApiService, "")

        val dummyGetMovieResponse = DataDummy.generateGetMovieResponse()
        val expectedResult =
            PagingSource.LoadResult.Page(
                data = dummyGetMovieResponse.movieResponses,
                prevKey = null,
                nextKey = 2
            )
        Mockito.`when`(movieApiService.getMovies(page = 1)).thenReturn(
            dummyGetMovieResponse
        )

        val result =  moviePagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 3,
                placeholdersEnabled = false
            )
        )

        Mockito.verify(movieApiService).getMovies(page = 1)
        assertEquals(
            expectedResult, result
        )
    }

    @Test
    fun `When Query Not Empty Should Search Return List Movie`() = runTest {
        val query = "the nun"
        moviePagingSource = MoviePagingSource(movieApiService, query)

        val dummyGetMovieResponse = DataDummy.generateGetMovieResponse()
        val expectedResult =
            PagingSource.LoadResult.Page(
                data = dummyGetMovieResponse.movieResponses,
                prevKey = null,
                nextKey = 2
            )
        Mockito.`when`(movieApiService.searchMovies(query = query, page = 1)).thenReturn(
            dummyGetMovieResponse
        )

        val result =  moviePagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 3,
                placeholdersEnabled = false
            )
        )

        Mockito.verify(movieApiService).searchMovies(query = query, page = 1)
        assertEquals(
            expectedResult, result
        )
    }

    @Test
    fun `When Error Should Return Error Message`() = runTest {
        moviePagingSource = MoviePagingSource(movieApiService, "")

        val error = HttpException(Response.error<Any>(500,
            "Test Server Error".toResponseBody("text/plain".toMediaTypeOrNull())
        ))
        val expectedResult =
            PagingSource.LoadResult.Error<Int, MovieResponse>(Exception("An unexpected error occurred"))

        Mockito.`when`(movieApiService.getMovies(page = 1)).then {
            throw error
        }

        val result = moviePagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 3,
                placeholdersEnabled = false
            )
        )

        Mockito.verify(movieApiService).getMovies(page = 1)
        assertEquals(
            expectedResult.throwable.message, (result as PagingSource.LoadResult.Error).throwable.message
        )
    }
}