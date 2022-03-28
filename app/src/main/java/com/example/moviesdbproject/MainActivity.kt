package com.example.moviesdbproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.moviesdbproject.viewmodel.MovieViewModel
import com.example.moviesdbproject.viewmodel.MoviesViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: MoviesViewModelFactory

    private val myViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MovieViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MoviesApp.moviesComponent.inject(this)
    }
}