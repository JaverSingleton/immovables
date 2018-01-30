package ru.vstu.immovables.repository.estimate

import io.reactivex.Single
import ru.vstu.immovables.repository.report.ReportData
import ru.vstu.immovables.repository.report.ReportRepository
import ru.vstu.immovables.ui.main.item.Field
import java.util.concurrent.TimeUnit

interface EstimateRepository {

    fun estimate(properties: List<Field>): Single<ReportData>

}

class Properties {
    companion object {
        const val ADDRESS = 1L
        const val AREA = 2L
    }
}

class EstimateRepositoryImpl(private val reportRepository: ReportRepository) : EstimateRepository {

    override fun estimate(properties: List<Field>): Single<ReportData> =
            Single.timer(2, TimeUnit.SECONDS)
                    .flatMap {
                        val location: Field.Location = properties.getField(Properties.ADDRESS)
                        val area: Field.NumberInput = properties.getField(Properties.AREA)
                        reportRepository.save(ReportData(
                                address = location.locationData?.name.orEmpty(),
                                metres = area.value.toLong(),
                                latitude = location.locationData?.location?.latitude ?: 0.0,
                                longitude = location.locationData?.location?.longitude ?: 0.0,
                                cost = 20000001,
                                filePath = "File Path"
                        ))
                    }

    @Suppress("UNCHECKED_CAST")
    private fun <T: Field>List<Field>.getField(id: Long): T = first { it.id == id } as T

}