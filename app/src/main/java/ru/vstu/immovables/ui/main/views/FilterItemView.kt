package ru.vstu.immovables.ui.main.views

import com.avito.konveyor.blueprint.ItemView

/**
 * Created by kkruchinin on 26.11.17.
 */
interface FilterItemView : ItemView {
    fun setTitle(title: String)
    fun setDescription(description: String)
}