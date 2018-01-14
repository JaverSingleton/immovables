package ru.vstu.immovables.ui.location.item

import com.avito.konveyor.blueprint.Item
import ru.vstu.immovables.repository.LocationData

class LocationSearchItem(
        override val id: Long,
        val location: LocationData
) : Item