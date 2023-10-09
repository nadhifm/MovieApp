package com.example.movieapp.domain.usecase

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import app.cash.turbine.test
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.repository.MovieRepository
import com.example.movieapp.utils.DataDummy
import com.example.movieapp.utils.MainCoroutineRule
import com.example.movieapp.utils.MyDiffCallback
import com.example.movieapp.utils.NoopListCallback
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetMoviesTest {

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    private lateinit var getMovies: GetMovies

    @Before
    fun setUp() {
        getMovies = GetMovies(movieRepository)
    }

    @Test
    fun `When Success Get Movies Should Return List Movie`() = runTest {
        val dummyListMovie = DataDummy.generateListMovie()
        val expectedPagingData = PagingData.from(dummyListMovie)
        Mockito.`when`(movieRepository.getMovies("")).thenReturn(flow {
            emit(expectedPagingData)
        })

        getMovies("").test {
            Mockito.verify(movieRepository).getMovies("")

            val differ = AsyncPagingDataDiffer(
                diffCallback = MyDiffCallback(),
                updateCallback = NoopListCallback(),
                workerDispatcher = mainCoroutineScopeRule.dispatcher
            )
            val result = awaitItem()
            differ.submitData(result)
            advanceUntilIdle()

            Assert.assertNotNull(result)
            Assert.assertEquals(dummyListMovie, differ.snapshot().items)
            awaitComplete()
        }
    }

    @Test
    fun `When Error Get Movies Should Return Error Message`() = runTest {
        val dummyErrorMessage = DataDummy.generateErrorMessage()
        val expectedPagingData = PagingData.empty<Movie>(LoadStates(
            refresh = LoadState.Error(Throwable(dummyErrorMessage)),
            prepend = LoadState.NotLoading(true),
            append = LoadState.NotLoading(true)
        ))
        Mockito.`when`(movieRepository.getMovies("")).thenReturn(flow {
            emit(expectedPagingData)
        })

        getMovies("").test {
            Mockito.verify(movieRepository).getMovies("")

            val differ = AsyncPagingDataDiffer(
                diffCallback = MyDiffCallback(),
                updateCallback = NoopListCallback(),
                workerDispatcher = mainCoroutineScopeRule.dispatcher
            )
            val result = awaitItem()
            differ.submitData(result)
            advanceUntilIdle()

            Assert.assertNotNull(result)
            differ.addLoadStateListener {
                Assert.assertEquals(dummyErrorMessage, (it.refresh as LoadState.Error).error.message)
            }
            awaitComplete()
        }
    }
}