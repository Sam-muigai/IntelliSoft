package com.samkt.intellisoft.features.home.di

import com.samkt.intellisoft.features.home.HomeScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
    viewModelOf(::HomeScreenViewModel)
}
