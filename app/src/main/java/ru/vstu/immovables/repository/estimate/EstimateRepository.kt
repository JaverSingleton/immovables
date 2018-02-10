package ru.vstu.immovables.repository.estimate

import com.google.android.gms.maps.model.LatLng
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
        const val ROOMS = 4L
        const val FLOOR = 6L
        const val MAX_FLOOR = 5L
    }
}

class EstimateRepositoryImpl(private val reportRepository: ReportRepository) : EstimateRepository {

    override fun estimate(properties: List<Field>): Single<ReportData> =
            Single.timer(2, TimeUnit.SECONDS)
                    .flatMap {
                        val location: Field.Location = properties.getField(Properties.ADDRESS)
                        val area: Field.NumberInput = properties.getField(Properties.AREA)
                        val rooms: Field.Select? = properties.getNullableField(Properties.ROOMS)
                        val areaValue = area.value.toLong()
                        val roomsValue = rooms?.selectedItem ?: 1
                        val locationValue = location.locationData!!.location
                        reportRepository.save(ReportData(
                                address = location.locationData?.name.orEmpty(),
                                metres = areaValue,
                                latitude = location.locationData?.location?.latitude ?: 0.0,
                                longitude = location.locationData?.location?.longitude ?: 0.0,
                                cost = calculateCost(areaValue, locationValue, roomsValue),
                                filePath = "File Path"
                        ))
                    }

    private fun calculateCost(
            area: Long,
            location: LatLng,
            roomsValue: Int
    ): Long {
        val rooms = if (roomsValue == 0) {
            1
        } else {
            roomsValue
        }

        val normalRooms = ((area - BASE_AREA) / ROOM_AREA)
        val normalRoomsCost = normalRooms * 500_000

        val microRooms = Math.max(rooms - Math.floor(normalRooms).toInt(), 0)
        val microRoomsCost = microRooms * 100_000

        val cost = 500_000 + normalRoomsCost + microRoomsCost
        return (cost * calculateFactor(location)).toLong()
    }

    private fun calculateFactor(location: LatLng): Float {
        val latDelta = Math.abs((location.latitude - CENTER_LAT) / CENTER_LAT)
        val lonDelta = Math.abs((location.longitude - CENTER_LON) / CENTER_LON)
        val latFactor = 1 - Math.min(latDelta / DELTA, 1.0)
        val lonFactor = 1 - Math.min(lonDelta / DELTA, 1.0)
        val deltaFactor = Math.sqrt(latFactor * lonFactor)
        return 1f + deltaFactor.toFloat() * 0.5f
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Field> List<Field>.getField(id: Long): T = first { it.id == id } as T

    @Suppress("UNCHECKED_CAST")
    private fun <T : Field> List<Field>.getNullableField(id: Long): T? = firstOrNull { it.id == id } as T?

}


private const val CENTER_LAT = 48.7055715
private const val CENTER_LON = 44.4913526

private val DELTA = 0.003
private val ROOM_AREA = 15.0
private val BASE_AREA = 18.0