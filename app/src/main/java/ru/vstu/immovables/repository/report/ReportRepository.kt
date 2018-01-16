package ru.vstu.immovables.repository.report

import io.reactivex.Single
import io.reactivex.rxkotlin.toSingle

interface ReportRepository {

    fun find(id: Long): Single<ReportData>

    fun save(report: ReportData): Single<ReportData>

    fun getAll(): Single<List<ReportData>>

}

class ReportRepositoryImpl(): ReportRepository {

    override fun find(id: Long): Single<ReportData> =
            ReportData(id = id, metres = 38, cost = 5000000000, filePath = "Path").toSingle()

    override fun save(report: ReportData): Single<ReportData> =
            ReportData(1, metres = 38, cost = 5000000000, filePath = "Path").toSingle()

    override fun getAll(): Single<List<ReportData>> =
            listOf<ReportData>().toSingle()
}