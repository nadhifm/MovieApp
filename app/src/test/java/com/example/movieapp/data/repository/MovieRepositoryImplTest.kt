package com.example.movieapp.data.repository

import app.cash.turbine.test
import com.example.movieapp.data.datasource.local.room.MovieDao
import com.example.movieapp.data.datasource.remote.network.MovieApiService
import com.example.movieapp.data.mapper.toMovieDetail
import com.example.movieapp.data.mapper.toMovieEntity
import com.example.movieapp.utils.DataDummy
import com.example.movieapp.utils.MainCoroutineRule
import com.example.movieapp.utils.Resource
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
class MovieRepositoryImplTest {
    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineRule()

    @Mock
    private lateinit var movieApiService: MovieApiService

    @Mock
    private lateinit var movieDao: MovieDao

    private lateinit var movieRepositoryImpl: MovieRepositoryImpl

    @Before
    fun setUp() {
        movieRepositoryImpl = MovieRepositoryImpl(movieApiService, movieDao)
    }

    @Test
    fun `When Success Get Movie Should Return Paging Data Movies`() = runTest {
        movieRepositoryImpl.getMovies("").test {
            val result = awaitItem()
            assertNotNull(result)
        }
    }

    @Test
    fun `When Success Get Movie Detail Should Return Success and Movie Detail`() = runTest {
        val dummyMovieDetailResponse = DataDummy.generateMovieDetailResponse()
        Mockito.`when`(movieApiService.getMovieDetail( 968051)).thenReturn(dummyMovieDetailResponse)

        movieRepositoryImpl.getMovieDetail(968051).test {
            Mockito.verify(movieApiService).getMovieDetail( 968051)

            var result = awaitItem()
            assertTrue(result is Resource.Loading)

            result = awaitItem()
            assertTrue(result is Resource.Success)
            assertNotNull(result.data)
            assertEquals(dummyMovieDetailResponse.toMovieDetail(), result.data)

            awaitComplete()
        }
    }

    @Test
    fun `When Error Get Movie Detail Should Return Error and Message`() = runTest {
        val dummyIOException = DataDummy.generateIOException()

        Mockito.`when`(movieApiService.getMovieDetail( 968051)).then {
            throw dummyIOException
        }

        movieRepositoryImpl.getMovieDetail(968051).test {
            Mockito.verify(movieApiService).getMovieDetail( 968051)

            var result = awaitItem()
            assertTrue(result is Resource.Loading)

            result = awaitItem()
            assertTrue(result is Resource.Error)
            assertNotNull(result.message)
            assertEquals(dummyIOException.message, result.message)

            awaitComplete()
        }
    }

    @Test
    fun `When Success Get Movie Favorite Should Return List Movie`() = runTest {
        val dummyListMovieEntity = DataDummy.generateListMovieEntity()

        Mockito.`when`(movieDao.getMovies()).thenReturn(flow {
            emit(dummyListMovieEntity)
        })

        movieRepositoryImpl.getMovieFavorites().test {
            Mockito.verify(movieDao).getMovies()
            val result = awaitItem()
            assertNotNull(result)
            assertEquals(dummyListMovieEntity.size, result.size)
            assertEquals(dummyListMovieEntity[0].id, result[0].id)

            awaitComplete()
        }
    }

    @Test
    fun `When Id In Database Should true`() = runTest {
        val dummyMovieEntity = DataDummy.generateMovieEntity()

        Mockito.`when`(movieDao.getMovieById(968051)).thenReturn(dummyMovieEntity)

        movieRepositoryImpl.checkIsFavorite(968051).test {
            Mockito.verify(movieDao).getMovieById(968051)

            val result = awaitItem()
            assertTrue(result)
            awaitComplete()
        }
    }

    @Test
    fun `When Id Not In Database Should true`() = runTest {
        Mockito.`when`(movieDao.getMovieById(968051)).thenReturn(null)

        movieRepositoryImpl.checkIsFavorite(968051).test {
            Mockito.verify(movieDao).getMovieById(968051)

            val result = awaitItem()
            assertFalse(result)
            awaitComplete()
        }
    }

    @Test
    fun `when Success Add To Favorite Should Return true`() = runTest {
        val dummyMovieDetail = DataDummy.generateMovieDetail()

        Mockito.`when`(movieDao.insertMovie(dummyMovieDetail.toMovieEntity())).thenReturn(Unit)

        movieRepositoryImpl.addToFavorite(dummyMovieDetail).test {
            Mockito.verify(movieDao).insertMovie(dummyMovieDetail.toMovieEntity())

            val result = awaitItem()
            assertTrue(result)
            awaitComplete()
        }
    }

    @Test
    fun `when Success Remove From Favorite Should Return false`() = runTest {
        val dummyMovieDetail = DataDummy.generateMovieDetail()

        Mockito.`when`(movieDao.deleteMovie(dummyMovieDetail.toMovieEntity())).thenReturn(Unit)

        movieRepositoryImpl.removeFromFavorite(dummyMovieDetail).test {
            Mockito.verify(movieDao).deleteMovie(dummyMovieDetail.toMovieEntity())

            val result = awaitItem()
            assertFalse(result)
            awaitComplete()
        }
    }
}