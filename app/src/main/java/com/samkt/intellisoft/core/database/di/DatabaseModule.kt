package com.samkt.intellisoft.core.database.di

import androidx.room.Room
import com.samkt.intellisoft.core.database.IntellisoftDatabase
import org.koin.dsl.module

val databaseModule = module {
    single<IntellisoftDatabase> {
        Room.databaseBuilder(
            get(),
            IntellisoftDatabase::class.java,
            "intellisoft_database.db",
        ).build()
    }
}
