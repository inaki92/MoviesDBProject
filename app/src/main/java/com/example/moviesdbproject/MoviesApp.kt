package com.example.moviesdbproject

import android.app.Application
import com.example.moviesdbproject.di.ApplicationModule
import com.example.moviesdbproject.di.DaggerMoviesComponent
import com.example.moviesdbproject.di.MoviesComponent

class MoviesApp : Application() {
    override fun onCreate() {
        super.onCreate()

        moviesComponent = DaggerMoviesComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    companion object{
        lateinit var moviesComponent: MoviesComponent
    }
}