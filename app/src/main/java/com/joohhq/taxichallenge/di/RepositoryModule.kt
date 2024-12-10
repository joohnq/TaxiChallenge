package com.joohhq.taxichallenge.di

import com.joohhq.taxichallenge.data.repository.TravelApiRepository
import com.joohhq.taxichallenge.data.repository.TravelApiRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::TravelApiRepositoryImpl) bind TravelApiRepository::class
}