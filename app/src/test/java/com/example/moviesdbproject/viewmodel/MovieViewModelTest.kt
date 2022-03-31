package com.example.moviesdbproject.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import com.example.moviesdbproject.model.MoviesResponse
import com.example.moviesdbproject.rest.MovieRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

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
    fun `live data test`() {
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
}