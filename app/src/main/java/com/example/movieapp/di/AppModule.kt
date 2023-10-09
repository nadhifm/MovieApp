package com.example.movieapp.di

import android.app.Application
import androidx.room.Room
import com.example.movieapp.data.datasource.local.room.MovieDatabase
import com.example.movieapp.data.datasource.remote.network.MovieApiService
import com.example.movieapp.data.repository.MovieRepositoryImpl
import com.example.movieapp.domain.repository.MovieRepository
import com.example.movieapp.domain.usecase.AddToFavorite
import com.example.movieapp.domain.usecase.CheckIsFavorite
import com.example.movieapp.domain.usecase.GetMovieDetail
import com.example.movieapp.domain.usecase.GetMovieFavorites
import com.example.movieapp.domain.usecase.GetMovies
import com.example.movieapp.domain.usecase.RemoveFromFavorite
import com.example.movieapp.utils.Constans.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMovieDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            MovieDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideMovieApiService(retrofit: Retrofit): MovieApiService = retrofit.create(MovieApiService::class.java)

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieApiService: MovieApiService,
        movieDatabase: MovieDatabase
    ): MovieRepository = MovieRepositoryImpl(movieApiService, movieDatabase.movieDao)

    @Provides
    @Singleton
    fun provideGetMovies(
        movieRepository: MovieRepository
    ): GetMovies = GetMovies(movieRepository)

    @Provides
    @Singleton
    fun provideGetMovieDetail(
        movieRepository: MovieRepository
    ): GetMovieDetail = GetMovieDetail(movieRepository)

    @Provides
    @Singleton
    fun provideGetMovieFavorites(
        movieRepository: MovieRepository
    ): GetMovieFavorites = GetMovieFavorites(movieRepository)

    @Provides
    @Singleton
    fun provideCheckIsFavorite(
        movieRepository: MovieRepository
    ): CheckIsFavorite = CheckIsFavorite(movieRepository)

    @Provides
    @Singleton
    fun provideAddToFavorite(
        movieRepository: MovieRepository
    ): AddToFavorite = AddToFavorite(movieRepository)

    @Provides
    @Singleton
    fun provideRemoveFromFavorite(
        movieRepository: MovieRepository
    ): RemoveFromFavorite = RemoveFromFavorite(movieRepository)
}