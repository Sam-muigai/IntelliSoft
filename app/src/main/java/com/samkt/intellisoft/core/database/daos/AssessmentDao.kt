package com.samkt.intellisoft.core.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samkt.intellisoft.core.database.entities.AssessmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AssessmentDao {

    @Query("SELECT * FROM assessments WHERE vitalId = :vitalId")
    fun getAssessmentsByVitalId(vitalId: Int): Flow<List<AssessmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssessment(assessment: AssessmentEntity): Long

    @Query("UPDATE assessments SET patientBackendId = :patientBackendId AND vitalBackendId = :vitalBackendId WHERE id = :assessmentId")
    suspend fun setBackendIdsForAssessment(
        assessmentId: Int,
        patientBackendId: String,
        vitalBackendId: String
    )
}