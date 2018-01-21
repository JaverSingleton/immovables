package ru.vstu.immovables.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "reports")
class Report(
        @PrimaryKey(autoGenerate = true) var id: Long = 0L,
        @ColumnInfo(name = "address") var address: String = "",
        @ColumnInfo(name = "metres") var metres: Long = 0L,
        @ColumnInfo(name = "cost") var cost: Long = 0L,
        @ColumnInfo(name = "file_path") var filePath: String = ""
)