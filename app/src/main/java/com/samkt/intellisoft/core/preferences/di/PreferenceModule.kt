package com.samkt.intellisoft.core.preferences.di

import androidx.datastore.preferences.core.Preferences
import com.samkt.intellisoft.core.preferences.AppPreferences
import com.samkt.intellisoft.core.preferences.AppPreferencesImpl
import org.koin.dsl.module

val preferencesModule = module {
    single<AppPreferences> { AppPreferencesImpl(get()) }
}