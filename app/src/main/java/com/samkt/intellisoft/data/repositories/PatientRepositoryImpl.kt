package com.samkt.intellisoft.data.repositories

import android.util.Log
import com.samkt.intellisoft.core.database.IntellisoftDatabase
import com.samkt.intellisoft.core.database.entities.AssessmentEntity
import com.samkt.intellisoft.core.database.entities.VitalsEntity
import com.samkt.intellisoft.core.networking.IntellisoftApiService
import com.samkt.intellisoft.core.networking.dtos.GetVisitsRequest
import com.samkt.intellisoft.core.networking.dtos.PatientData
import com.samkt.intellisoft.core.networking.helpers.ApiResponse
import com.samkt.intellisoft.data.mappers.toData
import com.samkt.intellisoft.data.mappers.toDomain
import com.samkt.intellisoft.data.mappers.toEntity
import com.samkt.intellisoft.domain.model.Assessment
import com.samkt.intellisoft.domain.model.Patient
import com.samkt.intellisoft.domain.model.Visit
import com.samkt.intellisoft.domain.model.Vitals
import com.samkt.intellisoft.domain.repositories.PatientRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import com.samkt.intellisoft.domain.helpers.Result

class PatientRepositoryImpl(
    db: IntellisoftDatabase,
    private val intellisoftApiService: IntellisoftApiService
) : PatientRepository {
    private val patientDao = db.patientDao()
    private val vitalsDao = db.vitalsDao()
    private val assessmentDao = db.assessmentDao()

    override suspend fun savePatient(patient: Patient): Int {
        return patientDao.savePatient(patient.toEntity()).toInt()
    }

    override fun getPatientByPatientNumber(patientNumber: String): Flow<Patient?> {
        return patientDao.getPatientByPatientNumber(patientNumber).map { it?.toDomain() }
    }

    override suspend fun saveAssessment(assessment: Assessment): Int {
        return assessmentDao.insertAssessment(assessment.toEntity()).toInt()
    }

    override fun getPatient(patientId: Int): Flow<Patient?> {
        return patientDao.getPatientById(patientId).map { it?.toDomain() }
    }

    override suspend fun saveVitalsInformation(vitals: Vitals): Int {
        return vitalsDao.saveVitals(vitals.toEntity()).toInt()
    }

    override suspend fun syncPatientData(patientId: Int): kotlin.Result<String> {
        val patientEntity = patientDao.getPatientById(patientId).first()
        if (patientEntity == null) {
            Log.w("Sync", "No patient found with ID: $patientId")
            return kotlin.Result.failure(Exception("No patient found with ID: $patientId"))
        }
        val patient = patientEntity.toDomain()

        return runCatching {
            intellisoftApiService.savePatient(patient.toData())
            patientDao.updatePatientSyncStatus(patientId, true)
            val patients = getPatients()
            val patientData = patients.find { it.unique == patient.patientNumber }
            patientData?.let {
                updateVitalsBackedId(patientId, it.id.toString())
            }
            val vitals = vitalsDao.getVitals(patientId).first()
            syncVitals(vitals)
            "Patient data synced successfully"
        }
    }

    override suspend fun getVisits(date: String): Result<List<Visit>> {
        val getVisitsRequest = GetVisitsRequest(date)
        return when (val response = intellisoftApiService.getVisits(getVisitsRequest)) {
            is ApiResponse.Error -> Result.Error(response.errorMessage)
            is ApiResponse.Success -> Result.Success(response.data.visits.map { it.toDomain() })
        }
    }

    private suspend fun syncVitals(vitals: List<VitalsEntity>) {
        coroutineScope {
            val vitalTasks = vitals.map { vitalsEntity ->
                async { saveVitalToBackend(vitalsEntity) }
            }
            vitalTasks.awaitAll()
        }
    }

    private suspend fun saveVitalToBackend(vitalsEntity: VitalsEntity) {
        runCatching {
            val response = intellisoftApiService.setVitals(vitalsEntity.toDomain().toData())
            val assessmentEntities = assessmentDao.getAssessmentsByVitalId(vitalsEntity.id).first()
            assessmentEntities.forEach { assessment ->
                updateAssessmentBackendIds(
                    assessmentId = assessment.id,
                    patientBackendId = vitalsEntity.patientBackendId,
                    vitalsBackendId = response.saveVitalsData.id.toString()
                )
            }
            val assessments = assessmentDao.getAssessmentsByVitalId(vitalsEntity.id).first()
            syncAssessments(assessments)
        }.onSuccess {
            vitalsDao.updateSyncStatus(vitalsEntity.id, true)
        }
    }

    private suspend fun syncAssessments(
        assessments: List<AssessmentEntity>
    ) {
        coroutineScope {
            val assessmentTask = assessments.map { assessment ->
                async {
                    saveAssessmentToBackend(assessment)
                }
            }
            assessmentTask.awaitAll()
        }
    }

    private suspend fun saveAssessmentToBackend(assessment: AssessmentEntity) {
        runCatching {
            intellisoftApiService.saveVisits(assessment.toDomain().toData())
        }.onSuccess {
            assessmentDao.updateSyncStatus(assessmentId = assessment.id, true)
        }
    }

    private suspend fun updateVitalsBackedId(patientId: Int, patientBackendId: String) {
        vitalsDao.setPatientBackendId(
            patientId = patientId,
            patientBackendId = patientBackendId
        )
    }

    private suspend fun updateAssessmentBackendIds(
        assessmentId: Int,
        patientBackendId: String,
        vitalsBackendId: String
    ) {
        assessmentDao.setBackendIdsForAssessment(
            assessmentId = assessmentId,
            patientBackendId = patientBackendId,
            vitalBackendId = vitalsBackendId
        )
    }

    private suspend fun getPatients(): List<PatientData> {
        return intellisoftApiService.getPatients().data
    }
}