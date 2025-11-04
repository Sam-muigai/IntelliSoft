package com.samkt.intellisoft.data.repositories

import com.samkt.intellisoft.core.database.IntellisoftDatabase
import com.samkt.intellisoft.data.mappers.toDomain
import com.samkt.intellisoft.data.mappers.toEntity
import com.samkt.intellisoft.domain.model.Assessment
import com.samkt.intellisoft.domain.model.Patient
import com.samkt.intellisoft.domain.model.Vitals
import com.samkt.intellisoft.domain.repositories.PatientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PatientRepositoryImpl(
    db: IntellisoftDatabase
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

}