package com.samkt.intellisoft.core.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.samkt.intellisoft.core.database.entities.PatientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientsDao {

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun savePatient(patientEntity: PatientEntity): Long

    @Query("SELECT * FROM patients WHERE id = :patientId")
    fun getPatientById(patientId: Int): Flow<PatientEntity>
}