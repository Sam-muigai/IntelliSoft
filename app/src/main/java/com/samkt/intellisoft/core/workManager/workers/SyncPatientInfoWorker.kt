package com.samkt.intellisoft.core.workManager.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.ForegroundInfo
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
        setForeground(getForegroundInfo())
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

    override suspend fun getForegroundInfo(): ForegroundInfo {
        val notificationId = 1
        val channelId = "sync_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Background Sync",
                NotificationManager.IMPORTANCE_MIN,
            ).apply {
                setSound(null, null)
                enableVibration(false)
                setShowBadge(false)
            }

            val manager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        // Notification
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(android.R.drawable.stat_sys_upload)
            .setContentTitle("Syncing data")
            .setContentText("Patient information is being synced...")
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setOngoing(true)
            .setSilent(true)
            .build()

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ForegroundInfo(
                notificationId,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC,
            )
        } else {
            ForegroundInfo(notificationId, notification)
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
