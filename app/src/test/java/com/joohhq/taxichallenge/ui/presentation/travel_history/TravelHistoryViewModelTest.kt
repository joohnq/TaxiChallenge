package com.joohhq.taxichallenge.ui.presentation.travel_history

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth
import com.joohhq.taxichallenge.data.repository.TravelApiRepository
import com.joohhq.taxichallenge.entities.Driver
import com.joohhq.taxichallenge.entities.Ride
import com.joohhq.taxichallenge.entities.response.UserRidesResponse
import com.joohhq.taxichallenge.ui.presentation.travel_history.event.TravelHistoryEvent
import com.joohhq.taxichallenge.ui.presentation.travel_history.state.TravelHistoryState
import com.joohhq.taxichallenge.ui.state.UiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okio.IOException
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TravelHistoryViewModelTest {
    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var repository: TravelApiRepository
    private lateinit var viewModel: TravelHistoryViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val userRidesResponse = UserRidesResponse(
        customerId = "id",
        rides = listOf(
            Ride(
                date = "2024-09-17T04:06:29",
                destination = "Destination",
                distance = 123.0,
                driver = Driver(
                    id = 1,
                    name = "Name"
                ),
                duration = "12:34",
                id = 1,
                origin = "Origin",
                value = 50.00
            ),
            Ride(
                date = "2024-09-15T04:06:29",
                destination = "Destination 2",
                distance = 456.0,
                driver = Driver(
                    id = 2,
                    name = "Name 2"
                ),
                duration = "12:35",
                id = 2,
                origin = "Origin 2",
                value = 100.00
            )
        )
    )
    private val userId = "userId"
    private val driverId = "driverId"

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
        viewModel = TravelHistoryViewModel(repository, testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test the initial state`() = runTest {
        viewModel.state.test {
            Truth.assertThat(awaitItem()).isEqualTo(TravelHistoryState())
        }
    }

    @Test
    fun `test the onSearch with successfully response`() = runTest {
        coEvery { repository.userRides(any<String>(), any<String>()) } returns userRidesResponse

        viewModel.state.test {
            Truth.assertThat(awaitItem()).isEqualTo(TravelHistoryState())

            viewModel.onEvent(TravelHistoryEvent.OnUserIdChange(userId))
            Truth.assertThat(awaitItem().userId).isEqualTo(userId)

            viewModel.onEvent(TravelHistoryEvent.OnDriverIdChange(driverId))
            Truth.assertThat(awaitItem().driverId).isEqualTo(driverId)

            viewModel.onEvent(TravelHistoryEvent.OnSearch)

            Truth.assertThat(awaitItem().userTravels).isEqualTo(UiState.Loading)

            Truth.assertThat(awaitItem().userTravels).isEqualTo(
                UiState.Success(userRidesResponse)
            )
        }
    }

    @Test
    fun `test the onEstimate with error response`() = runTest {
        val exception = IOException("Network error")

        coEvery { repository.userRides(any<String>(), any<String>()) } throws exception

        viewModel.state.test {
            Truth.assertThat(awaitItem()).isEqualTo(TravelHistoryState())

            viewModel.onEvent(TravelHistoryEvent.OnUserIdChange(userId))
            Truth.assertThat(awaitItem().userId).isEqualTo(userId)

            viewModel.onEvent(TravelHistoryEvent.OnDriverIdChange(driverId))
            Truth.assertThat(awaitItem().driverId).isEqualTo(driverId)

            viewModel.onEvent(TravelHistoryEvent.OnSearch)

            Truth.assertThat(awaitItem().userTravels).isEqualTo(UiState.Loading)

            Truth.assertThat(awaitItem().userTravels).isEqualTo(
                UiState.Error(exception.message.toString())
            )
        }
    }
}