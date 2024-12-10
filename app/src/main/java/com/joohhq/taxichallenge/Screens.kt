package com.joohhq.taxichallenge

import kotlinx.serialization.Serializable

object Screens {
    @Serializable
    data object TravelHistory

    @Serializable
    data object TravelRequest

    @Serializable
    data object TravelDetail
}