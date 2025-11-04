package com.samkt.intellisoft.domain.repositories

import com.samkt.intellisoft.domain.model.Patient
import com.samkt.intellisoft.domain.model.Vitals
import kotlinx.coroutines.flow.Flow

interface PatientRepository {

    suspend fun savePatient(patient: Patient): Int

    suspend fun saveVitalsInformation(vitals: Vitals): Int

    fun getPatient(patientId: Int): Flow<Patient>
}