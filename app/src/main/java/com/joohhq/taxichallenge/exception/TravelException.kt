package com.joohhq.taxichallenge.exception

sealed class TravelException(override val message: String) : Exception(message) {
    data object EmptyUserId : TravelException("User id cannot be empty")
    data object EmptyDriverId : TravelException("Driver id cannot be empty")
    data object EmptyOrigin : TravelException("Origin cannot be empty")
    data object EmptyDestination : TravelException("Destination cannot be empty")
    data object RideEstimateFailed : TravelException("Error in estimating ride")
    data object RideEstimateBodyNull : TravelException("Estimate ride request body is null")
    data object RideConfirmFailed : TravelException("Error in confirm ride")
    data object RideConfirmBodyNull : TravelException("Confirm ride request body is null")
    data object UserTravelsFailed : TravelException("Error in get user rides")
    data object UserTravelsBodyNull : TravelException("User rides request body is null")
    data object NoDriversToOriginDestination : TravelException("No drivers to this origin and destination")

}