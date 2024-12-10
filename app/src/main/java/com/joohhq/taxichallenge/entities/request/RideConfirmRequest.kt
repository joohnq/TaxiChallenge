package com.joohhq.taxichallenge.entities.request

import com.google.gson.annotations.SerializedName
import com.joohhq.taxichallenge.entities.Driver

data class RideConfirmRequest(
    @SerializedName("customer_id") val customerId: String,
    val destination: String,
    val distance: Int,
    val driver: Driver,
    val duration: Int,
    val origin: String,
    val value: Double
)