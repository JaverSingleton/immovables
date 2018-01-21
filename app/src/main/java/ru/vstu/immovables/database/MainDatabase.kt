package ru.vstu.immovables.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import ru.vstu.immovables.database.dao.ReportDao
import ru.vstu.immovables.database.entities.Report

@Database(
        entities = arrayOf(
                Report::class
        ),
        version = 1
)
abstract class MainDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "database"

        @JvmStatic
        fun createPersistenceDatabase(context: Context): MainDatabase =
                Room.databaseBuilder(context.applicationContext, MainDatabase::class.java, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build()

    }

    abstract fun getReportDao(): ReportDao
}