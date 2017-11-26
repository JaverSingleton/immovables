package ru.vstu.immovables.ui.presenters

import com.avito.konveyor.blueprint.ItemPresenter
import ru.vstu.immovables.ui.items.DropDownList
import ru.vstu.immovables.ui.views.DropDownListView

/**
 * Created by kkruchinin on 26.11.17.
 */
class DropDownListPresenter: ItemPresenter<DropDownListView, DropDownList> {
    override fun bindView(view: DropDownListView, item: DropDownList, position: Int) {
        view.setTitle(item.name)
        view.setDescription(item.description)
    }
}