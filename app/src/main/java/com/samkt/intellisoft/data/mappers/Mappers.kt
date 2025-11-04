package com.samkt.intellisoft.data.mappers

import com.samkt.intellisoft.core.database.entities.AssessmentEntity
import com.samkt.intellisoft.core.database.entities.PatientEntity
import com.samkt.intellisoft.core.database.entities.VitalsEntity
import com.samkt.intellisoft.core.networking.dtos.AddPatientRequest
import com.samkt.intellisoft.core.networking.dtos.LoginRequest
import com.samkt.intellisoft.core.networking.dtos.SaveVisitRequest
import com.samkt.intellisoft.core.networking.dtos.SaveVitalsRequest
import com.samkt.intellisoft.core.networking.dtos.SignUpRequest
import com.samkt.intellisoft.domain.model.Assessment
import com.samkt.intellisoft.domain.model.Login
import com.samkt.intellisoft.domain.model.Patient
import com.samkt.intellisoft.domain.model.SignUp
import com.samkt.intellisoft.domain.model.Vitals
import com.samkt.intellisoft.utils.formatDate
import kotlinx.coroutines.flow.update


fun SignUp.toData(): SignUpRequest {
    return SignUpRequest(
        email = email,
        password = password,
        firstname = firstname,
        lastname = lastname
    )
}

fun Login.toData(): LoginRequest {
    return LoginRequest(
        email = email,
        password = password
    )
}

fun Patient.toEntity(): PatientEntity {
    return PatientEntity(
        id = id,
        patientNumber = patientNumber,
        firstName = firstName,
        lastName = lastName,
        gender = gender,
        registrationDate = registrationDate,
        dateOfBirth = dateOfBirth,
    )
}

fun Patient.toData(): AddPatientRequest {
    return AddPatientRequest(
        dob = dateOfBirth.formatDate(),
        firstname = firstName,
        gender = gender,
        lastname = lastName,
        regDate = registrationDate.formatDate(),
        unique = patientNumber
    )
}


fun PatientEntity.toDomain(): Patient {
    return Patient(
        patientNumber = patientNumber,
        firstName = firstName,
        lastName = lastName,
        gender = gender,
        registrationDate = registrationDate,
        dateOfBirth = dateOfBirth,
        id = id
    )
}

fun Vitals.toEntity(): VitalsEntity {
    return VitalsEntity(
        id = id,
        height = height,
        weight = weight,
        visitDate = visitDate,
        patientId = patientId
    )
}


fun VitalsEntity.toDomain(): Vitals {
    return Vitals(
        id = id,
        height = height,
        weight = weight,
        visitDate = visitDate,
        patientId = patientId,
        patientBackendId = patientBackendId
    )
}

fun Vitals.toData(): SaveVitalsRequest {
    return SaveVitalsRequest(
        bmi = calculateBmi(height, weight),
        height = height,
        patientId = patientBackendId,
        visitDate = visitDate.formatDate(),
        weight = weight
    )
}

fun Assessment.toEntity(): AssessmentEntity {
    return AssessmentEntity(
        id = id,
        generalHealth = generalHealth,
        onDiet = onDiet,
        onDrugs = onDrugs,
        comments = comments,
        visitDate = visitDate,
        vitalId = vitalId,
    )
}


fun AssessmentEntity.toDomain(): Assessment {
    return Assessment(
        id = id,
        generalHealth = generalHealth,
        onDiet = onDiet,
        onDrugs = onDrugs,
        comments = comments,
        visitDate = visitDate,
        vitalId = vitalId,
        patientBackendId = patientBackendId,
        vitalsBackendId = vitalBackendId
    )
}

fun Assessment.toData(): SaveVisitRequest {
    return SaveVisitRequest(
        comments = comments,
        generalHealth = generalHealth,
        onDiet = onDiet,
        onDrugs = onDrugs,
        patientId = patientBackendId,
        visitDate = visitDate.formatDate(),
        vitalId = vitalsBackendId
    )
}

private fun calculateBmi(
    height: String,
    weight: String
): String {
    val heightInCm = height.toDoubleOrNull() ?: 0.0
    val weightInKg = weight.toDoubleOrNull() ?: 0.0
    val heightInMeters = heightInCm / 100
    return try {
        val bmi = weightInKg / (heightInMeters * heightInMeters)
        String.format("%.2f", bmi)
    } catch (e: Exception) {
        e.printStackTrace()
        "0.0"
    }
}