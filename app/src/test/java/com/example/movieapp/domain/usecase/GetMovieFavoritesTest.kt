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
class GetMovieFavoritesTest {

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    private lateinit var getMovieFavorites: GetMovieFavorites

    @Before
    fun setUp() {
        getMovieFavorites = GetMovieFavorites(movieRepository)
    }

    @Test
    fun `When Success Get Movie Favorite Should Return List Movie`() = runTest {
        val dummyListMovieFavorites = DataDummy.generateListMovie()
        Mockito.`when`(movieRepository.getMovieFavorites()).thenReturn(flow {
            emit(dummyListMovieFavorites)
        })

        getMovieFavorites().test {
            Mockito.verify(movieRepository).getMovieFavorites()

            val result = awaitItem()
            assertNotNull(result)
            assertEquals(dummyListMovieFavorites.size, result.size)
            assertEquals(dummyListMovieFavorites[0].id, result[0].id)
            awaitComplete()
        }
    }

    @Test
    fun `When Empty Get Movie Favorite Should Return Empty List`() = runTest {
        Mockito.`when`(movieRepository.getMovieFavorites()).thenReturn(flow {
            emit(emptyList())
        })

        getMovieFavorites().test {
            Mockito.verify(movieRepository).getMovieFavorites()

            val result = awaitItem()
            assertNotNull(result)
            assertEquals(0, result.size)
            awaitComplete()
        }
    }
}