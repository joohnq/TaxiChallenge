package com.joohhq.taxichallenge.data.repository

import com.joohhq.taxichallenge.data.network.TravelApiDataSource
import com.joohhq.taxichallenge.entities.request.RideConfirmRequest
import com.joohhq.taxichallenge.entities.request.RideEstimateRequest
import com.joohhq.taxichallenge.entities.response.ErrorResponse
import com.joohhq.taxichallenge.entities.response.RideEstimateResponse
import com.joohhq.taxichallenge.entities.response.UserRidesResponse
import com.joohhq.taxichallenge.exception.TravelException
import com.joohhq.taxichallenge.utils.Print
import kotlinx.serialization.json.Json
import retrofit2.Response

interface TravelApiRepository{
    suspend fun rideEstimate(request: RideEstimateRequest): RideEstimateResponse
    suspend fun rideConfirm(request: RideConfirmRequest): Boolean
    suspend fun userRides(userId: String, driverId: String): UserRidesResponse
}

class TravelApiRepositoryImpl(
    private val travelApiDataSource: TravelApiDataSource
): TravelApiRepository {
    override suspend fun rideEstimate(request: RideEstimateRequest): RideEstimateResponse =
        request(
            error = TravelException.RideEstimateFailed,
            bodyNull = TravelException.RideEstimateBodyNull,
        ) {
            travelApiDataSource.api.rideEstimate(request)
        }

    override suspend fun rideConfirm(request: RideConfirmRequest): Boolean =
        request(
            error = TravelException.RideConfirmFailed,
            bodyNull = TravelException.RideConfirmBodyNull,
        ) {
            travelApiDataSource.api.rideConfirm(request)
        }.success

    override suspend fun userRides(userId: String, driverId: String): UserRidesResponse =
        request(
            error = TravelException.UserTravelsFailed,
            bodyNull = TravelException.UserTravelsBodyNull,
        ) {
            travelApiDataSource.api.userRides(
                userId = userId,
                driverId = driverId
            )
        }

    private suspend fun <T> request(
        error: Exception,
        bodyNull: Exception,
        block: suspend () -> Response<T>
    ): T {
        val res: Response<T> = block()
        if (!res.isSuccessful) {
            val errorBody = res.errorBody()?.string() ?: throw error
            Print.i(errorBody)
            val errorBodyRes: ErrorResponse = Json.decodeFromString(errorBody)
            throw Exception(errorBodyRes.errorDescription)
        }
        if (res.body() == null) throw bodyNull
        return res.body()!!
    }
}