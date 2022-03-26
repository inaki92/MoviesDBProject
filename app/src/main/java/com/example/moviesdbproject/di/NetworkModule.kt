package com.example.moviesdbproject.di

import com.example.moviesdbproject.rest.MovieRepository
import com.example.moviesdbproject.rest.MovieRepositoryImpl
import com.example.moviesdbproject.rest.Services
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {
    @Provides
    fun providesNetworkService(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(Services.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(Services::class.java)

    @Provides
    fun providesOkHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    @Provides
    fun providesMovieRepository(movieServices: Services): MovieRepository =
        MovieRepositoryImpl(movieServices)
}