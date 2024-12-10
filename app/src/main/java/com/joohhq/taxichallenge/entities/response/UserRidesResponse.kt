package com.joohhq.taxichallenge.entities.response

import com.google.gson.annotations.SerializedName
import com.joohhq.taxichallenge.entities.Ride

data class UserRidesResponse(
    @SerializedName("customer_id") val customerId: String,
    val rides: List<Ride>
)