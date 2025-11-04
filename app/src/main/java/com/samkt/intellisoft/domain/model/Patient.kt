package com.samkt.intellisoft.domain.model

import java.time.LocalDate

data class Patient(
    val id: Int,
    val patientNumber: String,
    val registrationDate: LocalDate,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate,
    val gender: String,
)
