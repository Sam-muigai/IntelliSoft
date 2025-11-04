package com.samkt.intellisoft.domain.repositories

import com.samkt.intellisoft.domain.model.Patient

interface PatientRepository {

    suspend fun savePatient(patient: Patient)
}