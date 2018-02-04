package ru.vstu.immovables.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "accounts")
class Account(
        @PrimaryKey() var id: Long = 1L,
        @ColumnInfo(name = "login") var login: String = ""
)