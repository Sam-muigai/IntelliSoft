package com.samkt.intellisoft.core.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.samkt.intellisoft.core.database.entities.VitalsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VitalsDao {

    @Insert
    suspend fun insertVitals(vitalsEntity: VitalsEntity)

    @Query("SELECT * FROM vitals WHERE patientId = :patientId")
    fun getVitals(patientId: Int): Flow<List<VitalsEntity>>

    @Query("UPDATE vitals SET patientBackendId = :patientBackendId WHERE patientId = :patientId")
    fun setPatientBackendId(patientId: Int, patientBackendId: String)
}