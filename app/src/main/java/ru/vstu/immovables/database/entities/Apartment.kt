package ru.vstu.immovables.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "apartments")
class Apartment(
        @PrimaryKey(autoGenerate = true) val id: Int,
        @ColumnInfo(name = "total_space") val totalSpace: Int,
        @ColumnInfo(name = "living_space") val livingSpace: Int,
        @ColumnInfo(name = "room_count") val roomCount: Int,
        @ColumnInfo(name = "number") val number: Int,
        @ColumnInfo(name = "description") val description: String,
        @Embedded val address: Address
)