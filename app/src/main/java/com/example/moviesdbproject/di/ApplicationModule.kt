package com.example.moviesdbproject.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesdbproject.rest.MovieRepository
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(
    private val applicationContext: Context
) {

    @Provides
    fun providesContext(): Context {
        return applicationContext
    }

//    @Provides
//    fun providesMoviesViewModelFactory(moviesRepository: MovieRepository): ViewModelProvider.Factory

}