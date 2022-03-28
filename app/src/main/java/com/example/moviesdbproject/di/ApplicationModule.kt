package com.example.moviesdbproject.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.moviesdbproject.rest.MovieRepository
import com.example.moviesdbproject.viewmodel.MoviesViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(
    private val applicationContext: Context
) {

    @Provides
    fun providesContext(): Context {
        return applicationContext
    }

    @Provides
    @Singleton
    fun providesMoviesViewModelFactory(movieRepository: MovieRepository): ViewModelProvider.Factory =
        MoviesViewModelFactory(movieRepository)
}