package com.example.moviesdbproject.rest

import com.example.moviesdbproject.exceptions.EmptyMoviesResponseException
import com.example.moviesdbproject.exceptions.MoviesRequestErrorException
import retrofit2.Response

/**
 * executes network requests
 */
suspend fun <T> MovieRepository.executeRequest(
    resource: suspend () -> Response<T>,
    success: (response: T) -> Unit,
    error: (throwable: Throwable) -> Unit
) {
    try {
        val response = resource()
        if (response.isSuccessful) {
            response.body()?.let {
                success.invoke(it)
            } ?: throw EmptyMoviesResponseException()
        } else {
            throw MoviesRequestErrorException()
        }
    } catch (e: Exception) {
        error.invoke(e)
    }
}