package com.joohhq.taxichallenge.data.network

import com.joohhq.taxichallenge.entities.request.RideEstimateRequest
import com.joohhq.taxichallenge.entities.request.RideConfirmRequest
import com.joohhq.taxichallenge.entities.response.RideConfirmResponse
import com.joohhq.taxichallenge.entities.response.RideEstimateResponse
import com.joohhq.taxichallenge.entities.response.UserRidesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TravelAPI {
    @POST("/ride/estimate")
    suspend fun rideEstimate(@Body request: RideEstimateRequest): Response<RideEstimateResponse>

    @PATCH("/ride/confirm")
    suspend fun rideConfirm(@Body request: RideConfirmRequest): Response<RideConfirmResponse>

    @GET("/ride/{userId}")
    suspend fun userRides(
        @Path("userId") userId: String,
        @Query("driver_id") driverId: String
    ): Response<UserRidesResponse>
}