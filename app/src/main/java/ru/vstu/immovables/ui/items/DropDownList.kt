package ru.vstu.immovables.ui.items

import com.avito.konveyor.blueprint.Item

data class DropDownList(
        override val id: Long,
        val name: String,
        val description: String
): Item