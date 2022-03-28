package com.example.moviesdbproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.moviesdbproject.rest.MovieRepository
import com.example.moviesdbproject.viewmodel.MovieViewModel
import com.example.moviesdbproject.viewmodel.MoviesViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var movieRepository: MovieRepository

    private val viewmodel by lazy {
        ViewModelProvider(this, MoviesViewModelFactory(movieRepository))[MovieViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}