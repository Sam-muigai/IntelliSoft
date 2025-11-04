package com.samkt.intellisoft.features.vitals.di

import com.samkt.intellisoft.features.vitals.VitalsScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val vitalsModule = module {
    viewModelOf(::VitalsScreenViewModel)
}
