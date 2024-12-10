package com.joohhq.taxichallenge.ui.presentation.travel_request.state

import com.joohhq.taxichallenge.ui.state.UiState
import com.joohhq.taxichallenge.entities.response.RideEstimateResponse

data class TravelRequestState(
    val userId: String = "",
    val userIdError: String? = null,
    val origin: String = "",
    val originError: String? = null,
    val destination: String = "",
    val destinationError: String? = null,
    val rideEstimate: UiState<RideEstimateResponse> = UiState.Idle,
    val confirm: UiState<Boolean> = UiState.Idle
)