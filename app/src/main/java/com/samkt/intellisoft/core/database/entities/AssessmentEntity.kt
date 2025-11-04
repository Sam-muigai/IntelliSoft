package com.samkt.intellisoft.core.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "assessments",
    foreignKeys = [
        ForeignKey(
            entity = VitalsEntity::class,
            parentColumns = ["id"],
            childColumns = ["vitalId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AssessmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val generalHealth: String,
    val onDiet: String,
    val onDrugs: String,
    val comments: String,
    val visitDate: LocalDate,
    val patientBackendId: String = "",
    val vitalBackendId: String = "",
    val vitalId: Int,
    val isSynced: Boolean = false
)