package com.samkt.intellisoft.core.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class VitalsWithAssessments(
    @Embedded
    val vitals: VitalsEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "vitalId",
    )
    val assessments: List<AssessmentEntity>,
)
