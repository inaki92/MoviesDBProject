package com.example.moviesdbproject.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(
    private val applicationContext: Context
) {
    @Provides
    fun providesContext(): Context{
        return applicationContext
    }
}