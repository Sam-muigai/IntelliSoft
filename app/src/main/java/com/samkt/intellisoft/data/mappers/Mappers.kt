package com.samkt.intellisoft.data.mappers

import com.samkt.intellisoft.core.database.entities.PatientEntity
import com.samkt.intellisoft.core.networking.dtos.LoginRequest
import com.samkt.intellisoft.core.networking.dtos.SignUpRequest
import com.samkt.intellisoft.domain.model.Login
import com.samkt.intellisoft.domain.model.Patient
import com.samkt.intellisoft.domain.model.SignUp


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
        patientNumber = patientNumber,
        firstName = firstName,
        lastName = lastName,
        gender = gender,
        registrationDate = registrationDate,
        dateOfBirth = dateOfBirth,
    )
}