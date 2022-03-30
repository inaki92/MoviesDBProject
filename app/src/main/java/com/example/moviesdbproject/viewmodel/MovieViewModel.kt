package com.example.moviesdbproject.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.moviesdbproject.rest.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private var coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _moviesLiveData: MutableLiveData<MovieState> = MutableLiveData(MovieState.LOADING)
    val moviesLiveData: LiveData<MovieState> get() = _moviesLiveData

    fun getPlayNowMovies() {
        _moviesLiveData.postValue(MovieState.LOADING)
        viewModelScope.launch(coroutineDispatcher) {
            movieRepository.getPlayNowMovies()
            movieRepository.movieDetails.collect {
                _moviesLiveData.postValue(it)
            }
        }
    }

    fun getAllPopularMovies() {
        _moviesLiveData.postValue(MovieState.LOADING)
        viewModelScope.launch(coroutineDispatcher) {

        }
    }

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(coroutineDispatcher) {
            movieRepository.movieDetails.collect {
                // todo posting to the livedata
            }
        }
    }
}