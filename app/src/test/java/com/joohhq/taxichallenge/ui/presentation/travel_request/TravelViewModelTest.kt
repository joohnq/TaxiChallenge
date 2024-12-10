package com.joohhq.taxichallenge.ui.presentation.travel_request

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth
import com.joohhq.taxichallenge.data.repository.TravelApiRepository
import com.joohhq.taxichallenge.entities.Location
import com.joohhq.taxichallenge.entities.Option
import com.joohhq.taxichallenge.entities.Review
import com.joohhq.taxichallenge.entities.request.RideConfirmRequest
import com.joohhq.taxichallenge.entities.request.RideEstimateRequest
import com.joohhq.taxichallenge.entities.response.RideEstimateResponse
import com.joohhq.taxichallenge.ui.presentation.travel_request.event.TravelRequestEvent
import com.joohhq.taxichallenge.ui.presentation.travel_request.state.TravelRequestState
import com.joohhq.taxichallenge.ui.state.UiState
import com.joohhq.taxichallenge.ui.state.UiState.Companion.getValue
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

class TravelViewModelTest {
    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var repository: TravelApiRepository
    private lateinit var viewModel: TravelViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val id = "123"
    private val origin = "Origin"
    private val destination = "Destination"
    private val zeroValueRideEstimateResponse = RideEstimateResponse(
        location = Location(
            latitude = 0.0,
            longitude = 0.0
        ),
        distance = 0,
        duration = 0,
        options = listOf(),
        origin = Location(
            latitude = 0.0,
            longitude = 0.0
        ),
        destination = Location(
            latitude = 0.0,
            longitude =0.0
        )
    )
    private var rideEstimateResponse = RideEstimateResponse(
        location = Location(
            latitude = 22.00,
            longitude = 25.00
        ),
        distance = 24,
        duration = 776,
        options = listOf(
            Option(
                description = "Description",
                id = 1,
                name = "Name",
                review = Review(
                    comment = "Comment",
                    rating = 1
                ),
                value = 50.0,
                vehicle = "Vehicle"
            ),
            Option(
                description = "Description 2",
                id = 2,
                name = "Name 2",
                review = Review(
                    comment = "Comment 2",
                    rating = 2
                ),
                value = 100.0,
                vehicle = "Vehicle 2"
            )
        ),
        origin = Location(
            latitude = 1.00,
            longitude = 2.00
        ),
        destination = Location(
            latitude = 3.00,
            longitude = 4.00
        )
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
        viewModel = TravelViewModel(repository, testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test the initial state`() = runTest {
        viewModel.state.test {
            Truth.assertThat(awaitItem()).isEqualTo(TravelRequestState())
        }
    }

    @Test
    fun `test the onEstimate with successfully response`() = runTest {
        coEvery { repository.rideEstimate(any<RideEstimateRequest>()) } returns
                rideEstimateResponse

        viewModel.state.test {
            Truth.assertThat(awaitItem()).isEqualTo(TravelRequestState())

            viewModel.onEvent(TravelRequestEvent.OnUserIdChange(id))
            Truth.assertThat(awaitItem().userId).isEqualTo(id)

            viewModel.onEvent(TravelRequestEvent.OnOriginChange(origin))
            Truth.assertThat(awaitItem().origin).isEqualTo(origin)

            viewModel.onEvent(TravelRequestEvent.OnDestinationChange(destination))
            Truth.assertThat(awaitItem().destination).isEqualTo(destination)

            viewModel.onEvent(TravelRequestEvent.OnEstimate)

            Truth.assertThat(awaitItem().rideEstimate).isEqualTo(UiState.Loading)

            Truth.assertThat(awaitItem().rideEstimate).isEqualTo(
                UiState.Success(rideEstimateResponse)
            )
        }
    }

    @Test
    fun `test the onEstimate with error response`() = runTest {
        val exception = IOException("Network error")

        coEvery { repository.rideEstimate(any<RideEstimateRequest>()) } throws exception

        viewModel.state.test {
            Truth.assertThat(awaitItem()).isEqualTo(TravelRequestState())

            viewModel.onEvent(TravelRequestEvent.OnUserIdChange(id))
            Truth.assertThat(awaitItem().userId).isEqualTo(id)

            viewModel.onEvent(TravelRequestEvent.OnOriginChange(origin))
            Truth.assertThat(awaitItem().origin).isEqualTo(origin)

            viewModel.onEvent(TravelRequestEvent.OnDestinationChange(destination))
            Truth.assertThat(awaitItem().destination).isEqualTo(destination)

            viewModel.onEvent(TravelRequestEvent.OnEstimate)

            Truth.assertThat(awaitItem().rideEstimate).isEqualTo(UiState.Loading)

            Truth.assertThat(awaitItem().rideEstimate).isEqualTo(
                UiState.Error(exception.message.toString())
            )
        }
    }

    @Test
    fun `test the onEstimate with successfully response, but with zero values`() = runTest {
        coEvery { repository.rideEstimate(any<RideEstimateRequest>()) } returns
                zeroValueRideEstimateResponse

        viewModel.state.test {
            Truth.assertThat(awaitItem()).isEqualTo(TravelRequestState())

            viewModel.onEvent(TravelRequestEvent.OnUserIdChange(id))
            Truth.assertThat(awaitItem().userId).isEqualTo(id)

            viewModel.onEvent(TravelRequestEvent.OnOriginChange(origin))
            Truth.assertThat(awaitItem().origin).isEqualTo(origin)

            viewModel.onEvent(TravelRequestEvent.OnDestinationChange(destination))
            Truth.assertThat(awaitItem().destination).isEqualTo(destination)

            viewModel.onEvent(TravelRequestEvent.OnEstimate)

            Truth.assertThat(awaitItem().rideEstimate).isEqualTo(UiState.Loading)

            Truth.assertThat(awaitItem().rideEstimate).isEqualTo(
                UiState.Error("No drivers to this origin and destination")
            )
        }
    }

    @Test
    fun `test the onConfirm with successfully response`() = runTest {
        coEvery { repository.rideEstimate(any<RideEstimateRequest>()) } returns
                rideEstimateResponse
        coEvery { repository.rideConfirm(any<RideConfirmRequest>()) } returns true

        viewModel.onEvent(TravelRequestEvent.OnUserIdChange(id))
        viewModel.onEvent(TravelRequestEvent.OnOriginChange(origin))
        viewModel.onEvent(TravelRequestEvent.OnDestinationChange(destination))
        viewModel.onEvent(TravelRequestEvent.OnEstimate)
        viewModel.state.test {
            awaitItem() // initialState
            awaitItem() // loadingRideEstimate
            val successRideEstimate = awaitItem().rideEstimate
            Truth.assertThat(successRideEstimate).isEqualTo(
                UiState.Success(rideEstimateResponse)
            )

            val option = successRideEstimate.getValue().options.first()
            viewModel.onEvent(TravelRequestEvent.OnConfirm(option))

            Truth.assertThat(awaitItem().confirm).isEqualTo(UiState.Loading)
            Truth.assertThat(awaitItem().confirm).isEqualTo(UiState.Success(true))
        }
    }

    @Test
    fun `test the onConfirm with error response`() = runTest {
        val exception = IOException("Network error")
        coEvery { repository.rideEstimate(any<RideEstimateRequest>()) } returns
                rideEstimateResponse
        coEvery { repository.rideConfirm(any<RideConfirmRequest>()) } throws exception

        viewModel.onEvent(TravelRequestEvent.OnUserIdChange(id))
        viewModel.onEvent(TravelRequestEvent.OnOriginChange(origin))
        viewModel.onEvent(TravelRequestEvent.OnDestinationChange(destination))
        viewModel.onEvent(TravelRequestEvent.OnEstimate)
        viewModel.state.test {
            awaitItem() // initialState
            awaitItem() // loadingRideEstimate
            val successRideEstimate = awaitItem().rideEstimate
            Truth.assertThat(successRideEstimate).isEqualTo(
                UiState.Success(rideEstimateResponse)
            )

            val option = successRideEstimate.getValue().options.first()
            viewModel.onEvent(TravelRequestEvent.OnConfirm(option))

            Truth.assertThat(awaitItem().confirm).isEqualTo(UiState.Loading)
            Truth.assertThat(awaitItem().confirm)
                .isEqualTo(UiState.Error(exception.message.toString()))
        }
    }
}