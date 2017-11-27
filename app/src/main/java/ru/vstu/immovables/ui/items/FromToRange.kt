package ru.vstu.immovables.ui.items

import com.avito.konveyor.blueprint.Item

data class FromToRange(
        override val id: Long,
        val name: String,
        val description: String
): Item {
}