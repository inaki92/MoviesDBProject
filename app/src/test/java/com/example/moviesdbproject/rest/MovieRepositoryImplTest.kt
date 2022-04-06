package com.example.moviesdbproject.rest

import com.example.moviesdbproject.model.MoviesResponse
import com.example.moviesdbproject.viewmodel.MovieState
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieRepositoryImplTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testCoroutineScope = TestScope(testDispatcher)
    private val mockServiceApi = mockk<Services>(relaxed = true)

    private lateinit var target: MovieRepository

    @Before
    fun startup() {
        Dispatchers.setMain(testDispatcher)
        target = MovieRepositoryImpl(mockServiceApi)
    }

    @After
    fun shutdown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `get playing now movies when calling the server returns a success response`() = runTest {
        // Assign
        coEvery { mockServiceApi.getAllPlayNowMovies() } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns mockk {
                every { page } returns 100
                every { totalResults } returns 400
            }
        }

        // Action

        target.getPlayNowMovies().collect {
            // Assertion
            val success = it as MovieState.SUCCESS<MoviesResponse>
            assertThat(success.response).isNotNull()
            assertThat(success.response.page).isEqualTo(100)
            assertThat(success.response.totalResults).isEqualTo(400)
        }

        coVerify { mockServiceApi.getAllPlayNowMovies() }
    }

    @Test
    fun `get playing now movies when server response is not success returns a error with exception`() = runTest {
        // Assign
        coEvery { mockServiceApi.getAllPlayNowMovies() } returns mockk {
            every { isSuccessful } returns false
        }

        // Action

        target.getPlayNowMovies().collect {
            // Assertion
            val success = it as MovieState.ERROR
            assertThat(success.error).isNotNull()
            assertThat(success.error.localizedMessage).isEqualTo("Playing now movie unsuccessful")
        }

        coVerify { mockServiceApi.getAllPlayNowMovies() }
    }

    @Test
    fun `get playing now movies when server response throws an error exception`() = runTest {
        // Assign
        coEvery { mockServiceApi.getAllPlayNowMovies() } throws Exception("ERROR")

        // Action
        target.getPlayNowMovies().collect {
            // Assertion
            val success = it as MovieState.ERROR
            assertThat(success.error).isNotNull()
            assertThat(success.error.localizedMessage).isEqualTo("ERROR")
        }

        coVerify { mockServiceApi.getAllPlayNowMovies() }
    }

    @Test
    fun `get playing now movies when server response is success but return a null body, throws an exception`() = runTest {
        // Assign
        coEvery { mockServiceApi.getAllPlayNowMovies() } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns null
        }

        // Action

        target.getPlayNowMovies().collect {
            // Assertion
            val success = it as MovieState.ERROR
            assertThat(success.error).isNotNull()
            assertThat(success.error.localizedMessage).isEqualTo("Playing now movie no response")
        }

        coVerify { mockServiceApi.getAllPlayNowMovies() }
    }
}