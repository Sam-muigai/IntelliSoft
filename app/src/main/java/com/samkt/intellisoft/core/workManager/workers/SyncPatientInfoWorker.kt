package com.samkt.intellisoft.core.workManager.workers

import android.content.Context
import android.net.http.HttpException
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.samkt.intellisoft.domain.repositories.PatientRepository
import java.io.IOException

class SyncPatientInfoWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val patientRepository: PatientRepository,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        patientRepository.syncInformation()
        return try {
            patientRepository.syncInformation()
            Result.success()
        } catch (e: IOException) {
            e.printStackTrace()
            Result.retry()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

    companion object {
        private val SyncConstraints
            get() = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        fun startUpSyncWork() = OneTimeWorkRequestBuilder<SyncPatientInfoWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(SyncConstraints)
            .build()
    }
}

object Sync {
    fun initialize(context: Context) {
        WorkManager.getInstance(context).apply {
            enqueueUniqueWork(
                SYNC_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                SyncPatientInfoWorker.startUpSyncWork(),
            )
        }
    }
}

const val SYNC_WORK_NAME = "SyncWorkName"
