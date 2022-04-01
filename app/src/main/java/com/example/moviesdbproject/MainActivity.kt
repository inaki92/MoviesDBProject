package com.example.moviesdbproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.moviesdbproject.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val moviesViewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviesViewModel.moviesLiveData.observe(this) {
            Log.d("TAG", "onCreate: ${it.toString()}")
        }
    }

    override fun onResume() {
        super.onResume()
        moviesViewModel.getPlayNowMovies()
    }
}