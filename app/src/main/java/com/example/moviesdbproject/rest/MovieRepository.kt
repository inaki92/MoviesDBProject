package com.example.moviesdbproject.rest

import com.example.moviesdbproject.model.MoviesResponse
import com.example.moviesdbproject.viewmodel.MovieState
import kotlinx.coroutines.flow.*
import retrofit2.Response

interface MovieRepository {
    val movieDetails: StateFlow<MovieState>
    suspend fun getPlayNowMovies()
    suspend fun getAllPopularMovies(
        page: Int,
        language: String = Services.LANGUAGE
    ): Response<MoviesResponse>

    suspend fun getAllDetailsMovies(movieId: Int): Flow<MovieState>
}

class MovieRepositoryImpl(
    private val movieServices: Services
) : ApiRepositoryBase(), MovieRepository {

    override suspend fun getPlayNowMovies() {
        executeRequest { movieServices.getAllPlayNowMovies() }
    }

    override suspend fun getAllPopularMovies(
        page: Int,
        language: String
    ): Response<MoviesResponse> {
        return movieServices.getAllPopularMovies(page = page, language = language)
    }

    override suspend fun getAllDetailsMovies(movieId: Int): Flow<MovieState> =
        flow { executeRequest { movieServices.getAllDetailsMovies(movieId) } }
}