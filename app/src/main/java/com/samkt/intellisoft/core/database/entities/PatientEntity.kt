package com.samkt.intellisoft.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate


@Entity(tableName = "patients")
data class PatientEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val patientNumber: String,
    val registrationDate: LocalDate,
    val firstName:String,
    val lastName:String,
    val dateOfBirth: LocalDate,
    val gender: String,
    val isSynced: Boolean = false
)
