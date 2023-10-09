package com.example.movieapp.domain.usecase

import app.cash.turbine.test
import com.example.movieapp.domain.repository.MovieRepository
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
class CheckIsFavoriteTest {

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    private lateinit var checkIsFavorite: CheckIsFavorite

    @Before
    fun setUp() {
        checkIsFavorite = CheckIsFavorite(movieRepository)
    }

    @Test
    fun `When Movie Is Favorite Should Return True`() = runTest {
        Mockito.`when`(movieRepository.checkIsFavorite(1)).thenReturn(flow {
            emit(true)
        })

        checkIsFavorite(1).test {
            Mockito.verify(movieRepository).checkIsFavorite(1)

            val result = awaitItem()
            assertNotNull(result)
            assertTrue(result)
            awaitComplete()
        }
    }

    @Test
    fun `When Movie Is Not Favorite Should Return False`() = runTest {
        Mockito.`when`(movieRepository.checkIsFavorite(1)).thenReturn(flow {
            emit(false)
        })

        checkIsFavorite(1).test {
            Mockito.verify(movieRepository).checkIsFavorite(1)

            val result = awaitItem()
            assertNotNull(result)
            assertFalse(result)
            awaitComplete()
        }
    }
}