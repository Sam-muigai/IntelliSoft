package com.samkt.intellisoft.features.signUp.di

import com.samkt.intellisoft.features.signUp.SignUpViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val signUpModule = module {
    viewModelOf(::SignUpViewModel)
}
