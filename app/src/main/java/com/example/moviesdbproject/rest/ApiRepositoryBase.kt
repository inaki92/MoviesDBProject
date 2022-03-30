package com.example.moviesdbproject.rest

import com.example.moviesdbproject.exceptions.EmptyMoviesResponseException
import com.example.moviesdbproject.exceptions.MoviesRequestErrorException
import com.example.moviesdbproject.viewmodel.MovieState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Response

/**
 * Base api repository class
 */
open class ApiRepositoryBase {

    private val _movieDetails: MutableStateFlow<MovieState> =
        MutableStateFlow(MovieState.LOADING)

    val movieDetails: StateFlow<MovieState>
        get() = _movieDetails

    /**
     * executes network requests
     */
    protected suspend fun <T> executeRequest(
        resource: suspend () -> Response<T>
    ) {
        try {
            val response = resource()
            if (response.isSuccessful) {
                response.body()?.let {
                    _movieDetails.value = MovieState.SUCCESS(it)
                } ?: throw EmptyMoviesResponseException()
            } else {
                throw MoviesRequestErrorException()
            }
        } catch (e: Exception) {
            _movieDetails.value = MovieState.ERROR(e)
        }
    }
}