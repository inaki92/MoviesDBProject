package com.example.moviesdbproject.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import com.example.moviesdbproject.model.MoviesResponse
import com.example.moviesdbproject.model.details.MoviesDetails
import com.example.moviesdbproject.rest.MovieRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.lang.Error
import java.lang.Exception

@ExperimentalCoroutinesApi
class MovieViewModelTest {

    @get:Rule var rule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private val mockRepository = mockk<MovieRepository>(relaxed = true)

    private lateinit var target: MovieViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        target = MovieViewModel(mockRepository, testDispatcher)
    }

    @After
    fun shutdown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `get play now movies when trying to load from server returns loading state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<MovieState>()
        target.moviesLiveData.observeForever {
            stateList.add(it)
        }
        // Action
        target.getPlayNowMovies()

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(2)
        assertThat(stateList[0]).isInstanceOf(MovieState.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(MovieState.LOADING::class.java)
    }

    @Test
    fun `get play now movies when trying to load from server returns success state`() {
        // AAA
        // Assign
        every { mockRepository.getPlayNowMovies() } returns flowOf(MovieState.SUCCESS(mockk<MoviesResponse>()))
        val stateList = mutableListOf<MovieState>()
        target.moviesLiveData.observeForever {
            stateList.add(it)
        }
        // Action

        target.getPlayNowMovies()

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(3)
        assertThat(stateList[0]).isInstanceOf(MovieState.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(MovieState.LOADING::class.java)
        assertThat(stateList[2]).isInstanceOf(MovieState.SUCCESS::class.java)
    }

    @Test
    fun `get play now movie trying to load from server error state`() {
        // AAA
        // Assign
        every{ mockRepository.getPlayNowMovies() } returns flowOf(MovieState.
        ERROR(Exception("Error")))
        val stateList = mutableListOf<MovieState>()
        target.moviesLiveData.observeForever {
            stateList.add(it)
        }

        // Action
        target.getPlayNowMovies()

        // Assertion
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(3)
        assertThat(stateList[0]).isInstanceOf(MovieState.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(MovieState.LOADING::class.java)
        assertThat(stateList[2]).isInstanceOf(MovieState.ERROR::class.java)
    }

    @Test
    fun `get movie details when trying to load items from the server returns loading state`(){
        // AAA
        // Assign
        every{ mockRepository.movieDetails } returns MutableStateFlow(MovieState.LOADING)
        val stateList = mutableListOf<MovieState>()
        target.moviesLiveData.observeForever {
            stateList.add(it)
        }

        // Action
        target.getMovieDetails(1)

        // Assertion
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(2)
        assertThat(stateList[0]).isInstanceOf(MovieState.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(MovieState.LOADING::class.java)
    }

    @Test
    fun `get movie details when trying to load items from the server returns success state`() {
        // AAA
        // Assign
        every{ mockRepository.movieDetails } returns MutableStateFlow(
            MovieState.SUCCESS(
                mockk<MoviesDetails>()
            )
        )
        val stateList = mutableListOf<MovieState>()
        target.moviesLiveData.observeForever {
            stateList.add(it)
        }

        // Action
        target.getMovieDetails(1)

        // Assertion
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(3)
        assertThat(stateList[0]).isInstanceOf(MovieState.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(MovieState.LOADING::class.java)
        assertThat(stateList[2]).isInstanceOf(MovieState.SUCCESS::class.java)
    }
}