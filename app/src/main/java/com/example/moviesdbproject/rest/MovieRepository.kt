package com.example.moviesdbproject.rest

import com.example.moviesdbproject.exceptions.EmptyMoviesResponseException
import com.example.moviesdbproject.exceptions.MoviesRequestErrorException
import com.example.moviesdbproject.model.MoviesResponse
import com.example.moviesdbproject.viewmodel.MovieState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response

interface MovieRepository {
    val movieDetails: StateFlow<MovieState>
    suspend fun getPlayNowMovies()
    suspend fun getAllPopularMovies(page: Int, language: String = Services.LANGUAGE): Response<MoviesResponse>
    suspend fun getAllDetailsMovies(movieId: Int, coroutineScope: CoroutineScope)
}

class MovieRepositoryImpl(
    private val movieServices: Services
) : MovieRepository {

    private val _movieDetails: MutableStateFlow<MovieState> =
        MutableStateFlow(MovieState.LOADING)

    override val movieDetails: StateFlow<MovieState>
        get() = _movieDetails

    override suspend fun getPlayNowMovies() {
        executeRequest (
            { movieServices.getAllPlayNowMovies() },
            {_movieDetails.value = MovieState.SUCCESS(it)},
            {_movieDetails.value = MovieState.ERROR(it)}
        )
    }

    override suspend fun getAllPopularMovies(page: Int,language: String): Response<MoviesResponse> {
        return movieServices.getAllPopularMovies(page = page, language = language)
    }

    override suspend fun getAllDetailsMovies(movieId: Int, coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            try {
                val response = movieServices.getAllDetailsMovies(movieId)
                if (response.isSuccessful){
                    response.body() ?.let {
                        _movieDetails.value = MovieState.SUCCESS(it)
                    } ?: throw Exception("Movie details no response null")
                } else {
                    throw Exception("Movie details unsuccessful")
                }
            } catch (e : Exception) {
                _movieDetails.value = MovieState.ERROR(e)
            }
        }
    }
}