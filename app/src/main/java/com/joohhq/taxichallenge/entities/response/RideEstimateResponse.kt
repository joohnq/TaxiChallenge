package com.joohhq.taxichallenge.entities.response

import com.joohhq.taxichallenge.entities.Location
import com.joohhq.taxichallenge.entities.Option

data class RideEstimateResponse(
    val location: Location,
    val distance: Int,
    val duration: Int,
    val options: List<Option>,
    val origin: Location,
    val destination: Location
)