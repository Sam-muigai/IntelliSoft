package com.samkt.intellisoft.features.assessment.di

import com.samkt.intellisoft.features.assessment.AssessmentScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val assessmentModule = module {
    viewModelOf(::AssessmentScreenViewModel)
}
