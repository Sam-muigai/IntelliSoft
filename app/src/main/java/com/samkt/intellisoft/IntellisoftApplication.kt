package com.samkt.intellisoft

import android.app.Application
import com.samkt.intellisoft.core.networking.di.networkModule
import com.samkt.intellisoft.data.di.dataModule
import com.samkt.intellisoft.features.home.di.homeModule
import com.samkt.intellisoft.features.login.di.loginModule
import com.samkt.intellisoft.features.signUp.di.signUpModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class IntellisoftApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val applicationModules = listOf(
            networkModule,
            dataModule,
            signUpModule,
            loginModule,
            homeModule
        )
        startKoin {
            androidContext(this@IntellisoftApplication)
            androidLogger()
            modules(applicationModules)
        }
    }


}