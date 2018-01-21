package ru.vstu.immovables.repository.report

import io.reactivex.Single
import ru.vstu.immovables.database.dao.ReportDao

interface ReportRepository {

    fun find(id: Long): Single<ReportData>

    fun save(report: ReportData): Single<ReportData>

    fun getAll(): Single<List<ReportData>>

}

class ReportRepositoryImpl(val reportDao: ReportDao) : ReportRepository {

    override fun find(id: Long): Single<ReportData> =
            reportDao.getReportByIdOnce(id)
                    .map { ReportData(report = it) }
//            ReportData(id = id, address = "Дом #1", metres = 38, cost = 5000000000, filePath = "Path").toSingle()

    override fun save(report: ReportData): Single<ReportData> =
            Single.fromCallable { reportDao.insert(report.toEntity()) }
                    .flatMap { find(it) }
//            ReportData(1, address = "Дом #1", metres = 38, cost = 5000000000, filePath = "Path").toSingle()

    override fun getAll(): Single<List<ReportData>> =
            reportDao.getAllReportsOnce()
                    .map { it.map { ReportData(it) } }
//            find(1).map { listOf(it) }
//            listOf<ReportData>().toSingle()
}