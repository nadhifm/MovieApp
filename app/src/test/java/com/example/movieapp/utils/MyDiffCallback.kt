package com.example.movieapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.movieapp.domain.model.Movie

class MyDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}