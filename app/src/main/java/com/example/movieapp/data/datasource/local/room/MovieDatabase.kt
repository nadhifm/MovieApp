package com.example.movieapp.data.datasource.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.data.datasource.local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao

    companion object {
        const val DATABASE_NAME = "movie_db"
    }
}