package com.joohhq.taxichallenge.ui.presentation.travel_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joohhq.taxichallenge.exception.TravelException
import com.joohhq.taxichallenge.ui.state.UiState
import com.joohhq.taxichallenge.data.repository.TravelApiRepository
import com.joohhq.taxichallenge.entities.response.UserRidesResponse
import com.joohhq.taxichallenge.ui.presentation.travel_history.event.TravelHistoryEvent
import com.joohhq.taxichallenge.ui.presentation.travel_history.state.TravelHistoryState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class TravelHistoryViewModel(
    private val repository: TravelApiRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = MutableStateFlow(TravelHistoryState())
    val state: StateFlow<TravelHistoryState> = _state.asStateFlow()

    fun onEvent(event: TravelHistoryEvent) {
        when (event) {
            is TravelHistoryEvent.OnUserIdChange -> onUserIdChange(event.id)
            is TravelHistoryEvent.OnUserIdErrorChange -> onUserIdErrorChange(event.error)
            is TravelHistoryEvent.OnDriverIdChange -> onDriverIdChange(event.id)
            is TravelHistoryEvent.OnDriverIdErrorChange -> onDriverIdErrorChange(event.error)
            TravelHistoryEvent.OnSearch -> onSearch()
            TravelHistoryEvent.ResetState -> resetState()
        }
    }

    private fun onSearch() = viewModelScope.launch(dispatcher) {
        val state = state.value
        try {
            if (state.userId.isEmpty()) throw TravelException.EmptyUserId
            if (state.driverId.isEmpty()) throw TravelException.EmptyDriverId

            handleUserTravelsRequest()
        } catch (e: TravelException.EmptyUserId) {
            onEvent(TravelHistoryEvent.OnUserIdErrorChange(e.message))
        } catch (e: TravelException.EmptyDriverId) {
            onEvent(TravelHistoryEvent.OnDriverIdErrorChange(e.message))
        }
    }

    private suspend fun handleUserTravelsRequest() = try {
        changeUserTravelsStatus(UiState.Loading)
        val res = repository.userRides(state.value.userId, state.value.driverId)
        changeUserTravelsStatus(UiState.Success(res))
    } catch (e: IOException) {
        changeUserTravelsStatus(UiState.Error("Network error"))
    } catch (e: HttpException) {
        changeUserTravelsStatus(UiState.Error("Server error"))
    } catch (e: Exception) {
        changeUserTravelsStatus(UiState.Error(e.message.toString()))
    }

    private fun onUserIdChange(id: String) {
        _state.update { it.copy(userId = id, userIdError = null) }
    }

    private fun onUserIdErrorChange(error: String?) {
        _state.update { it.copy(userIdError = error) }
    }

    private fun onDriverIdChange(id: String) {
        _state.update { it.copy(driverId = id, userIdError = null) }
    }

    private fun onDriverIdErrorChange(error: String?) {
        _state.update { it.copy(driverIdError = error) }
    }

    private fun changeUserTravelsStatus(status: UiState<UserRidesResponse>) {
        _state.update { it.copy(userTravels = status) }
    }

    private fun resetState() {
        _state.update { TravelHistoryState() }
    }
}