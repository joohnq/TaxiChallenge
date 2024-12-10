package com.joohhq.taxichallenge.di

import com.joohhq.taxichallenge.data.network.TravelApiDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    singleOf<GsonConverterFactory>(GsonConverterFactory::create)
    single<Retrofit>{
        val converter = get<GsonConverterFactory>()
        Retrofit.Builder()
            .baseUrl("https://xd5zl5kk2yltomvw5fb37y3bm40vsyrx.lambda-url.sa-east-1.on.aws")
            .addConverterFactory(converter)
            .build()
    }
    singleOf(::TravelApiDataSource)
}