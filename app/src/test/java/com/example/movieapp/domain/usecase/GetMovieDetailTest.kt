package com.example.movieapp.domain.usecase

import app.cash.turbine.test
import com.example.movieapp.domain.model.MovieDetail
import com.example.movieapp.domain.repository.MovieRepository
import com.example.movieapp.utils.DataDummy
import com.example.movieapp.utils.MainCoroutineRule
import com.example.movieapp.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
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
class GetMovieDetailTest {

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    private lateinit var getMovieDetail: GetMovieDetail

    @Before
    fun setUp() {
        getMovieDetail = GetMovieDetail(movieRepository)
    }

    @Test
    fun `When Success Get Movie Detail Should Return Movie Detail`() = runTest {
        val dummyMovieDetail = DataDummy.generateMovieDetail()
        val resourceLoading = Resource.Loading<MovieDetail>()
        val expectedResource = Resource.Success(dummyMovieDetail)
        Mockito.`when`(movieRepository.getMovieDetail(968051)).thenReturn(flow {
            emit(resourceLoading)
            emit(expectedResource)
        })

        getMovieDetail(968051).test {
            var result = awaitItem()
            Assert.assertEquals(resourceLoading, result)

            result = awaitItem()
            Assert.assertNotNull(result)
            Assert.assertEquals(expectedResource, result)
            Assert.assertEquals(dummyMovieDetail, result.data)
            awaitComplete()
        }
    }

    @Test
    fun `When Error Get Movie Detail Should Return Error Message`() = runTest {
        val dummyErrorMessage = DataDummy.generateErrorMessage()
        val resourceLoading = Resource.Loading<MovieDetail>()
        val expectedResource = Resource.Error<MovieDetail>(dummyErrorMessage)
        Mockito.`when`(movieRepository.getMovieDetail(968051)).thenReturn(flow {
            emit(resourceLoading)
            emit(expectedResource)
        })

        getMovieDetail(968051).test {
            var result = awaitItem()
            Assert.assertEquals(resourceLoading, result)

            result = awaitItem()
            Assert.assertNotNull(result)
            Assert.assertEquals(expectedResource, result)
            Assert.assertEquals(dummyErrorMessage, result.message)
            awaitComplete()
        }
    }
}