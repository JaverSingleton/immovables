package ru.vstu.immovables.repository.estimate

import io.reactivex.Single
import ru.vstu.immovables.repository.report.ReportData
import ru.vstu.immovables.repository.report.ReportRepository
import ru.vstu.immovables.ui.main.item.PropertyItem
import java.util.concurrent.TimeUnit

interface EstimateRepository {

    fun estimate(properties: List<PropertyItem>): Single<ReportData>

}

class EstimateRepositoryImpl(private val reportRepository: ReportRepository) : EstimateRepository {

    override fun estimate(properties: List<PropertyItem>): Single<ReportData> =
            Single.timer(2, TimeUnit.SECONDS)
                    .flatMap {
                        reportRepository.save(ReportData(
                                address = "Address",
                                metres = 28,
                                cost = 20000001,
                                filePath = "File Path"
                        ))
                    }

}