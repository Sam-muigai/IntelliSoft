package com.samkt.intellisoft.data.repositories

import com.samkt.intellisoft.core.database.IntellisoftDatabase
import com.samkt.intellisoft.data.mappers.toEntity
import com.samkt.intellisoft.domain.model.Patient
import com.samkt.intellisoft.domain.repositories.PatientRepository

class PatientRepositoryImpl(
    private val db: IntellisoftDatabase
) : PatientRepository {
    private val patientDao = db.patientDao()
    override suspend fun savePatient(patient: Patient) {
        patientDao.savePatient(patient.toEntity())
    }
}