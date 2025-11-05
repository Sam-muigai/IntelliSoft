package com.samkt.intellisoft

import android.app.Application
import com.samkt.intellisoft.core.database.di.databaseModule
import com.samkt.intellisoft.core.networking.di.networkModule
import com.samkt.intellisoft.core.preferences.di.preferencesModule
import com.samkt.intellisoft.core.workManager.di.workManagerModule
import com.samkt.intellisoft.data.di.dataModule
import com.samkt.intellisoft.features.assessment.di.assessmentModule
import com.samkt.intellisoft.features.home.di.homeModule
import com.samkt.intellisoft.features.login.di.loginModule
import com.samkt.intellisoft.features.patientRegistration.di.patientRegistrationModule
import com.samkt.intellisoft.features.signUp.di.signUpModule
import com.samkt.intellisoft.features.vitals.di.vitalsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin

class IntellisoftApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@IntellisoftApplication)
            androidLogger()
            workManagerFactory()
            modules(
                networkModule,
                dataModule,
                signUpModule,
                loginModule,
                homeModule,
                patientRegistrationModule,
                vitalsModule,
                assessmentModule,
                databaseModule,
                preferencesModule,
                workManagerModule,
            )
        }
    }
}
