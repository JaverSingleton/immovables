package ru.vstu.immovables.ui.items

import com.avito.konveyor.blueprint.Item

data class DateRange(
        override val id: Long,
        val name: String,
        val description: String
) : Item {
}