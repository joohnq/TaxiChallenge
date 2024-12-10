package com.joohhq.taxichallenge.ui.presentation.travel_request.event

import com.joohhq.taxichallenge.entities.Option

sealed class TravelRequestEvent {
    data class OnUserIdChange(val id: String) : TravelRequestEvent()
    data class OnUserIdErrorChange(val error: String? = null) : TravelRequestEvent()
    data class OnOriginChange(val origin: String) : TravelRequestEvent()
    data class OnOriginErrorChange(val error: String? = null) : TravelRequestEvent()
    data class OnDestinationChange(val destination: String) : TravelRequestEvent()
    data class OnDestinationErrorChange(val error: String? = null) : TravelRequestEvent()
    data object OnEstimate : TravelRequestEvent()
    data class OnConfirm(val option: Option) : TravelRequestEvent()
    data object ResetState : TravelRequestEvent()
}