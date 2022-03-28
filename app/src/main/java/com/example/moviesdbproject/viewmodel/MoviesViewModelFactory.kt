package com.example.moviesdbproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesdbproject.rest.MovieRepository
import javax.inject.Inject

class MoviesViewModelFactory @Inject constructor(
    private val moviesRepository: MovieRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieViewModel(moviesRepository) as T
    }
}