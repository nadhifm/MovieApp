package com.example.movieapp.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val releaseDate: String,
    val voteAverage: Double,
    val backdropPath: String,
)
