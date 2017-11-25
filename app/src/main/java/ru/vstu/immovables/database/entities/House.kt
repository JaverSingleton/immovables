package ru.vstu.immovables.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "houses")
data class House(
        @PrimaryKey(autoGenerate = true) val id: Int
)