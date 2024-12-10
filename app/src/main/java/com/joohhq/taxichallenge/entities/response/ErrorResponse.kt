package com.joohhq.taxichallenge.entities.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("error_code") val errorCode: String,
    @SerialName("error_description") val errorDescription: String
)