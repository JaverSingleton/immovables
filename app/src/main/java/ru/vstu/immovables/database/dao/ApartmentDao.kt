package ru.vstu.immovables.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import io.reactivex.Single
import ru.vstu.immovables.database.entities.Apartment

@Dao
interface ApartmentDao {

    @Query("SELECT * FROM apartments")
    fun getApartmentStream(): Flowable<List<Apartment>>

    @Query("SELECT * FROM apartments")
    fun getApartmentOnce(): Single<List<Apartment>>

    @Insert
    fun insert(apartment: Apartment)

    @Delete
    fun delete(apartment: Apartment)

    @Query("DELETE FROM apartments")
    fun deleteAll()

}