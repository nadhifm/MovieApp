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
class AddToFavoriteTest {

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    private lateinit var addToFavorite: AddToFavorite

    @Before
    fun setUp() {
        addToFavorite = AddToFavorite(movieRepository)
    }

    @Test
    fun `When Success Save Movie To Favorite Should Return True`() = runTest {
        val dummyMovieDetail = DataDummy.generateMovieDetail()
        Mockito.`when`(movieRepository.addToFavorite(dummyMovieDetail)).thenReturn(flow {
            emit(true)
        })

        addToFavorite(dummyMovieDetail).test {
            Mockito.verify(movieRepository).addToFavorite(dummyMovieDetail)

            val result = awaitItem()
            assertNotNull(result)
            assertTrue(result)
            awaitComplete()
        }
    }
}