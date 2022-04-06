package com.example.moviesdbproject.rest

import com.example.moviesdbproject.model.MoviesResponse
import com.example.moviesdbproject.viewmodel.MovieState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response

interface MovieRepository {
   val movieDetails: StateFlow<MovieState>
   fun getPlayNowMovies(): Flow<MovieState>
   suspend fun getAllPopularMovies(page: Int, language : String = Services.LANGUAGE): Response<MoviesResponse>
   fun getAllDetailsMovies(movieId : Int, coroutineScope: CoroutineScope)
}

class MovieRepositoryImpl(
   private val movieServices: Services
):MovieRepository{

   private val _movieDetails: MutableStateFlow<MovieState> = MutableStateFlow(MovieState.LOADING)
   override val movieDetails: StateFlow<MovieState>
      get() = _movieDetails

   override fun getPlayNowMovies(): Flow<MovieState> = flow {
      try {
         val response = movieServices.getAllPlayNowMovies()
         if (response.isSuccessful){
            response.body()?.let {
               emit(MovieState.SUCCESS(it))
            } ?: throw Exception("Playing now movie no response")
         } else {
            throw Exception("Playing now movie unsuccessful")
         }
      } catch (e : Exception) {
         emit(MovieState.ERROR(e))
      }
   }

   override suspend fun getAllPopularMovies(page: Int,language: String): Response<MoviesResponse> {
     return movieServices.getAllPopularMovies(page = page, language = language)
   }

   override fun getAllDetailsMovies(movieId: Int, coroutineScope: CoroutineScope) {
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