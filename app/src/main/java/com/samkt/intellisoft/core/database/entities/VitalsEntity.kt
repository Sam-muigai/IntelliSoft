package com.samkt.intellisoft.core.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate


@Entity(
    tableName = "vitals",
    foreignKeys = [
        ForeignKey(
            entity = PatientEntity::class,
            parentColumns = ["id"],
            childColumns = ["patientId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class VitalsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val height: String,
    val weight: String,
    val patientId: Int,
    val patientBackendId: String = "",
    val isSynced: Boolean = false,
    val visitDate: LocalDate
)