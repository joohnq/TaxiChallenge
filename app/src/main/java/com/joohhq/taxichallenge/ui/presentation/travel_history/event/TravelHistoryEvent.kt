package com.joohhq.taxichallenge.ui.presentation.travel_history.event

sealed class TravelHistoryEvent {
    data class OnUserIdChange(val id: String) : TravelHistoryEvent()
    data class OnUserIdErrorChange(val error: String? = null) : TravelHistoryEvent()

    data class OnDriverIdChange(val id: String) : TravelHistoryEvent()
    data class OnDriverIdErrorChange(val error: String? = null) : TravelHistoryEvent()

    data object OnSearch : TravelHistoryEvent()
    data object ResetState : TravelHistoryEvent()
}