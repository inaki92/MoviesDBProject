package com.example.moviesdbproject.exceptions

class EmptyMoviesResponseException: Exception("Movies response is empty")

class MoviesRequestErrorException: Exception("Something went wrong while doing request to server")