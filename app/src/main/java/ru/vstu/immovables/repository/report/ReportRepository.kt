package ru.vstu.immovables.repository.report

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.vstu.immovables.database.dao.ReportDao
import ru.vstu.immovables.repository.account.AccountRepository

interface ReportRepository {

    fun find(id: Long): Single<ReportData>

    fun save(report: ReportData): Single<ReportData>

    fun getAll(): Single<List<ReportData>>

}

class ReportRepositoryImpl(
        private val reportDao: ReportDao,
        private val accountRepository: AccountRepository
) : ReportRepository {

    override fun find(id: Long): Single<ReportData> =
            reportDao.getReportByIdOnce(id)
                    .subscribeOn(Schedulers.io())
                    .map { ReportData(report = it) }

    override fun save(report: ReportData): Single<ReportData> =
            Single.fromCallable { reportDao.insert(report.toEntity(accountRepository.getLogin())) }
                    .subscribeOn(Schedulers.io())
                    .flatMap { find(it) }

    override fun getAll(): Single<List<ReportData>> =
            reportDao.getAllReportsOnce(accountRepository.getLogin())
                    .subscribeOn(Schedulers.io())
                    .map { it.map { ReportData(it) } }

}