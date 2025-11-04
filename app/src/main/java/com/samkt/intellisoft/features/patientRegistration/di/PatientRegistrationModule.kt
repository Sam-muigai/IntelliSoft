package com.samkt.intellisoft.features.patientRegistration.di

import com.samkt.intellisoft.features.patientRegistration.PatientRegistrationScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val patientRegistrationModule = module {
    viewModelOf(::PatientRegistrationScreenViewModel)
}
