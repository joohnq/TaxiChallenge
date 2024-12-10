package com.joohhq.taxichallenge.di

import com.joohhq.taxichallenge.ui.presentation.travel_history.TravelHistoryViewModel
import com.joohhq.taxichallenge.ui.presentation.travel_request.TravelViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::TravelViewModel)
    viewModelOf(::TravelHistoryViewModel)
}