package ru.vstu.immovables.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "accounts")
class Account(
        @PrimaryKey(autoGenerate = true) var id: Long = 0L,
        @ColumnInfo(name = "login") var login: String = "",
        @ColumnInfo(name = "password") var password: String = ""
)