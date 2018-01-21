package ru.vstu.immovables.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import io.reactivex.Single
import ru.vstu.immovables.database.entities.Report


@Dao
interface ReportDao {

    @Query("SELECT * FROM reports")
    fun getAllReportStream(): Flowable<List<Report>>

    @Query("SELECT * FROM reports")
    fun getAllReportsOnce(): Single<List<Report>>

    @Query("SELECT * FROM reports WHERE id = :arg0")
    fun getReportByIdOnce(id: Long): Single<Report>

    @Insert
    fun insert(report: Report): Long

    @Delete
    fun delete(report: Report)

    @Query("DELETE FROM reports")
    fun deleteAll()
}