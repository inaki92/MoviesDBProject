package com.example.moviesdbproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesdbproject.rest.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
            try {
                val response = movieRepository.getPlayNowMovies()
                if (response.isSuccessful){
                    response.body() ?.let {
                        _moviesLiveData.postValue(MovieState.SUCCESS(it))
                    }?: throw Exception("playing now movie no response")
                }
                else {
                    throw Exception("playing now movie unsuccessful")
                }
            } catch (e : Exception){
                _moviesLiveData.postValue(MovieState.ERROR(e))
            }
        }
    }
}