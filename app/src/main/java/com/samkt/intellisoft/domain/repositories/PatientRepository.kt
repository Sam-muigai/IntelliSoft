package com.samkt.intellisoft.domain.repositories

import com.samkt.intellisoft.domain.model.Assessment
import com.samkt.intellisoft.domain.model.Patient
import com.samkt.intellisoft.domain.model.Vitals
import kotlinx.coroutines.flow.Flow
import com.samkt.intellisoft.domain.helpers.Result
import com.samkt.intellisoft.domain.model.Visit

interface PatientRepository {

    suspend fun savePatient(patient: Patient): Int

    suspend fun saveVitalsInformation(vitals: Vitals): Int

    fun getPatient(patientId: Int): Flow<Patient?>

    fun getPatientByPatientNumber(patientNumber: String): Flow<Patient?>

    suspend fun saveAssessment(assessment: Assessment): Int

    suspend fun syncPatientData(patientId: Int): kotlin.Result<String>

    suspend fun getVisits(date: String): Result<List<Visit>>
}