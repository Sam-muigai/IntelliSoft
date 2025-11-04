package com.samkt.intellisoft.core.database.entities

import androidx.room.Embedded
import androidx.room.Relation

class PatientWithVitals(
    @Embedded
    val patient: PatientEntity,

    @Relation(
        entity = VitalsEntity::class,
        parentColumn = "id",
        entityColumn = "patientId",
    )
    val vitalsWithAssessments: List<VitalsWithAssessments>,
)
