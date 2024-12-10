package com.joohhq.taxichallenge.data.network

import retrofit2.Retrofit

class TravelApiDataSource(
    private val retrofit: Retrofit
) {
    val api: TravelAPI = retrofit.create(TravelAPI::class.java)
}