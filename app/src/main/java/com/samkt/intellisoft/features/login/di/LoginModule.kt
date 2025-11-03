package com.samkt.intellisoft.features.login.di

import com.samkt.intellisoft.features.login.LoginScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val loginModule = module {
    viewModelOf(::LoginScreenViewModel)
}
