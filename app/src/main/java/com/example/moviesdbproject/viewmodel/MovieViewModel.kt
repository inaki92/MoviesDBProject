package com.example.moviesdbproject.viewmodel

import androidx.lifecycle.*
import com.example.moviesdbproject.rest.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor (
    private val movieRepository: MovieRepository,
    private var coroutineDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _moviesLiveData:MutableLiveData<MovieState> = MutableLiveData(MovieState.LOADING)
    val moviesLiveData : LiveData<MovieState> get() = _moviesLiveData

    fun getPlayNowMovies() {
        _moviesLiveData.postValue(MovieState.LOADING)

        viewModelScope.launch(coroutineDispatcher) {
            movieRepository.getPlayNowMovies()
                .collect { state ->
                    _moviesLiveData.postValue(state)
                }
        }
    }

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(coroutineDispatcher) {
            movieRepository.movieDetails.collect { state ->
                _moviesLiveData.postValue(state)
            }
        }
        movieRepository.getAllDetailsMovies(movieId, viewModelScope)
    }
}