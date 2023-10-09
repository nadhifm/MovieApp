package com.example.movieapp.domain.usecase

import app.cash.turbine.test
import com.example.movieapp.domain.repository.MovieRepository
import com.example.movieapp.utils.DataDummy
import com.example.movieapp.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class RemoveFromFavoriteTest {

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    private lateinit var removeFromFavorite: RemoveFromFavorite

    @Before
    fun setUp() {
        removeFromFavorite = RemoveFromFavorite(movieRepository)
    }

    @Test
    fun `When Success Remove Movie From Favorite Should Return False`() = runTest {
        val dummyMovieDetail = DataDummy.generateMovieDetail()
        Mockito.`when`(movieRepository.removeFromFavorite(dummyMovieDetail)).thenReturn(flow {
            emit(false)
        })

        removeFromFavorite(dummyMovieDetail).test {
            Mockito.verify(movieRepository).removeFromFavorite(dummyMovieDetail)

            val result = awaitItem()
            assertNotNull(result)
            assertFalse(result)
            awaitComplete()
        }
    }
}