package com.joohhq.taxichallenge.entities.request

import com.google.gson.annotations.SerializedName

data class RideEstimateRequest(
    @SerializedName("customer_id") val customerId: String,
    val destination: String,
    val origin: String
)