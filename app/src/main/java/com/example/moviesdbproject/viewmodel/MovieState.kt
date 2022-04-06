package com.example.moviesdbproject.viewmodel

sealed interface MovieState{
    object LOADING : MovieState
    class SUCCESS<T>(val response: T): MovieState
    class ERROR(val error:Throwable):MovieState
}