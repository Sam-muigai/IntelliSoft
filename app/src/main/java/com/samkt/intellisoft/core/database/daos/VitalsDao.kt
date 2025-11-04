package com.samkt.intellisoft.core.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.samkt.intellisoft.core.database.entities.VitalsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VitalsDao {

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun saveVitals(vitalsEntity: VitalsEntity): Long

    @Query("SELECT * FROM vitals WHERE patientId = :patientId")
    fun getVitals(patientId: Int): Flow<List<VitalsEntity>>

    @Query("UPDATE vitals SET patientBackendId = :patientBackendId WHERE patientId = :patientId")
    suspend fun setPatientBackendId(patientId: Int, patientBackendId: String)

    @Query("UPDATE vitals SET isSynced = :isSynced WHERE id = :vitalsId")
    suspend fun updateSyncStatus(vitalsId: Int, isSynced: Boolean)
}
