package com.example.moviesdbproject.rest

import com.example.moviesdbproject.model.MoviesResponse
import com.example.moviesdbproject.model.details.MoviesDetails
import retrofit2.Response

interface MovieRepository {
   suspend fun getPlayNowMovies(): Response<MoviesResponse>
   suspend fun getAllPopularMovies(page: Int, language : String = Services.LANGUAGE): Response<MoviesResponse>
   suspend fun getAllDetailsMovies(movieId : Int): Response<MoviesDetails>
}

class MovieRepositoryImpl(
   private val movieServices: Services
):MovieRepository{

   override suspend fun getPlayNowMovies(): Response<MoviesResponse> {
     return movieServices.getAllPlayNowMovies()
   }

   override suspend fun getAllPopularMovies(page: Int,language: String): Response<MoviesResponse> {
     return movieServices.getAllPopularMovies(page = page, language = language)
   }

   override suspend fun getAllDetailsMovies(movieId: Int): Response<MoviesDetails> {
      return movieServices.getAllDetailsMovies(movieID = movieId)
   }
}