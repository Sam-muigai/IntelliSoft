package com.samkt.intellisoft.core.workManager.di

import com.samkt.intellisoft.core.workManager.status.SyncManager
import com.samkt.intellisoft.core.workManager.status.WorkManagerSyncManager
import com.samkt.intellisoft.core.workManager.workers.SyncPatientInfoWorker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.dsl.module

val workManagerModule = module {
    single<SyncManager> { WorkManagerSyncManager(get()) }
    workerOf(::SyncPatientInfoWorker)
}
