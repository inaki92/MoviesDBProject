package com.example.moviesdbproject.di

import android.app.Application
import com.example.moviesdbproject.MainActivity
import dagger.Component

@Component(modules = [ApplicationModule::class, NetworkModule::class])
interface MoviesComponent {

    fun inject(mainActivity: MainActivity)
}