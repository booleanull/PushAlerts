package org.booleanull.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


object Migrations {

    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE alarm ADD COLUMN isFavorite INTEGER DEFAULT 0 NOT NULL")
        }
    }
}