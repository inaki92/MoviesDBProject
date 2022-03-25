package com.example.moviesdbproject.rest

import com.example.moviesdbproject.model.MoviesResponse
import com.example.moviesdbproject.model.details.MoviesDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Services {

    @GET(NOW_PLAYING)
    suspend fun getAllPlayNowMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int = PAGE
    ): Response<MoviesResponse>

    @GET(PATH_POPULAR)
    suspend fun getAllPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE,
        @Query("page") page: Int = PAGE
    ): Response<MoviesResponse>

    @GET("{movie_id}")
    suspend fun getAllDetailsMovies(
        @Path("movie_id") movieID: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE
    ): Response<MoviesDetails>

    companion object{


        const val BASE_URL = "https://api.themoviedb.org/3/movie/"
        private const val API_KEY = "819950d4cf35be1fb70d8746bc0796bf"
        const val LANGUAGE = "en-US"
        private const val PAGE = 1

        //https://api.themoviedb.org/3/movie/now_playing?api_key=819950d4cf35be1fb70d8746bc0796bf&language=en-US&page=1
        private const val NOW_PLAYING = "now_playing"
        //https://api.themoviedb.org/3/movie/popular?api_key=819950d4cf35be1fb70d8746bc0796bf&language=en-US&page=1
        private const val PATH_POPULAR = "popular"
        // https://api.themoviedb.org/3/movie/508947?api_key=819950d4cf35be1fb70d8746bc0796bf&language=en-US



    }

}