package com.joohhq.taxichallenge.ui.presentation.travel_history.state

import com.joohhq.taxichallenge.ui.state.UiState
import com.joohhq.taxichallenge.entities.response.UserRidesResponse

data class TravelHistoryState(
    val userId: String = "",
    val userIdError: String? = null,
    val driverId: String = "",
    val driverIdError: String? = null,
    val userTravels: UiState<UserRidesResponse> = UiState.Idle,
)