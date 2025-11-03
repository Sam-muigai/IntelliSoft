package com.samkt.intellisoft.features.addPatient.di

import com.samkt.intellisoft.features.addPatient.AddPatientScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val addPatientModule = module {
    viewModelOf(::AddPatientScreenViewModel)
}