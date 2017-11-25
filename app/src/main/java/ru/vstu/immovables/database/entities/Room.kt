package ru.vstu.immovables.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "rooms")
data class Room(
        @PrimaryKey(autoGenerate = true) val id: Int
)