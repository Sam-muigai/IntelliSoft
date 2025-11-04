package com.samkt.intellisoft.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.samkt.intellisoft.core.database.daos.AssessmentDao
import com.samkt.intellisoft.core.database.daos.PatientsDao
import com.samkt.intellisoft.core.database.daos.VitalsDao
import com.samkt.intellisoft.core.database.entities.AssessmentEntity
import com.samkt.intellisoft.core.database.entities.PatientEntity
import com.samkt.intellisoft.core.database.entities.VitalsEntity

@Database(
    entities = [
        AssessmentEntity::class,
        PatientEntity::class,
        VitalsEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class IntellisoftDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientsDao
    abstract fun assessmentDao(): AssessmentDao
    abstract fun vitalsDao(): VitalsDao
}
