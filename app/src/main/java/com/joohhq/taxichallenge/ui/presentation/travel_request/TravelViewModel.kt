package com.joohhq.taxichallenge.ui.presentation.travel_request

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohhq.taxichallenge.data.repository.TravelApiRepository
import com.joohhq.taxichallenge.entities.Driver
import com.joohhq.taxichallenge.entities.Option
import com.joohhq.taxichallenge.entities.request.RideConfirmRequest
import com.joohhq.taxichallenge.entities.request.RideEstimateRequest
import com.joohhq.taxichallenge.entities.response.RideEstimateResponse
import com.joohhq.taxichallenge.exception.TravelException
import com.joohhq.taxichallenge.ui.presentation.travel_request.event.TravelRequestEvent
import com.joohhq.taxichallenge.ui.presentation.travel_request.state.TravelRequestState
import com.joohhq.taxichallenge.ui.state.UiState
import com.joohhq.taxichallenge.ui.state.UiState.Companion.getValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class TravelViewModel(
    private val repository: TravelApiRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = MutableStateFlow(TravelRequestState())
    val state: StateFlow<TravelRequestState> = _state.asStateFlow()

    fun onEvent(event: TravelRequestEvent) {
        when (event) {
            is TravelRequestEvent.OnDestinationChange -> onDestinationChange(event.destination)
            is TravelRequestEvent.OnDestinationErrorChange -> onDestinationErrorChange(event.error)
            is TravelRequestEvent.OnOriginChange -> onOriginChange(event.origin)
            is TravelRequestEvent.OnOriginErrorChange -> onOriginErrorChange(event.error)
            is TravelRequestEvent.OnUserIdChange -> onUserIdChange(event.id)
            is TravelRequestEvent.OnUserIdErrorChange -> onUserIdErrorChange(event.error)
            TravelRequestEvent.OnEstimate -> onEstimate()
            is TravelRequestEvent.OnConfirm -> onConfirm(event.option)
            is TravelRequestEvent.ResetState -> resetState()
        }
    }

    private fun resetState() {
        _state.update { TravelRequestState() }
    }

    private fun onConfirm(option: Option) = viewModelScope.launch(dispatcher) {
        try {
            changeConfirmStatus(UiState.Loading)
            val res = repository.rideConfirm(
                RideConfirmRequest(
                    customerId = state.value.userId,
                    origin = state.value.origin,
                    destination = state.value.destination,
                    distance = state.value.rideEstimate.getValue().distance,
                    driver = Driver(
                        id = option.id,
                        name = option.name
                    ),
                    duration = state.value.rideEstimate.getValue().duration,
                    value = option.value,
                )
            )
            changeConfirmStatus(UiState.Success(res))
        } catch (e: Exception) {
            e.printStackTrace()
            changeConfirmStatus(UiState.Error(e.message.toString()))
        } catch (e: HttpException) {
            changeConfirmStatus(UiState.Error("Network erro"))
        } catch (e: IOException) {
            changeConfirmStatus(UiState.Error("Network error"))
        }
    }

    private fun onEstimate() = viewModelScope.launch(dispatcher) {
        val state = state.value
        try {
            if (state.userId.isEmpty()) throw TravelException.EmptyUserId
            if (state.origin.isEmpty()) throw TravelException.EmptyOrigin
            if (state.destination.isEmpty()) throw TravelException.EmptyDestination

            handleRideEstimateRequest()
        } catch (e: TravelException.EmptyUserId) {
            onEvent(TravelRequestEvent.OnUserIdErrorChange(e.message))
        } catch (e: TravelException.EmptyOrigin) {
            onEvent(TravelRequestEvent.OnOriginErrorChange(e.message))
        } catch (e: TravelException.EmptyDestination) {
            onEvent(TravelRequestEvent.OnDestinationErrorChange(e.message))
        }
    }

    private suspend fun handleRideEstimateRequest() = try {
        changeRideEstimateStatus(UiState.Loading)
        val res = repository.rideEstimate(
            RideEstimateRequest(
                customerId = state.value.userId,
                origin = state.value.origin,
                destination = state.value.destination
            )
        )
        if (res.origin.latitude == 0.0 || res.origin.longitude == 0.0) throw TravelException.NoDriversToOriginDestination
        changeRideEstimateStatus(UiState.Success(res))
    } catch (e: IOException) {
        changeRideEstimateStatus(UiState.Error("Network error"))
    } catch (e: HttpException) {
        changeRideEstimateStatus(UiState.Error("Server error"))
    } catch (e: Exception) {
        changeRideEstimateStatus(UiState.Error(e.message.toString()))
    }

    private fun onDestinationChange(destination: String) {
        _state.update { it.copy(destination = destination, destinationError = null) }
    }

    private fun onDestinationErrorChange(error: String?) {
        _state.update { it.copy(destinationError = error) }
    }

    private fun onOriginChange(origin: String) {
        _state.update { it.copy(origin = origin, originError = null) }
    }

    private fun onOriginErrorChange(error: String?) {
        _state.update { it.copy(originError = error) }
    }

    private fun onUserIdChange(userId: String) {
        _state.update { it.copy(userId = userId, userIdError = null) }
    }

    private fun onUserIdErrorChange(error: String?) {
        _state.update { it.copy(userIdError = error) }
    }

    private fun changeRideEstimateStatus(status: UiState<RideEstimateResponse>) {
        _state.update { it.copy(rideEstimate = status) }
    }

    private fun changeConfirmStatus(status: UiState<Boolean>) {
        _state.update { it.copy(confirm = status) }
    }
}