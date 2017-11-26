package ru.vstu.immovables.ui.presenters

import com.avito.konveyor.blueprint.ItemPresenter
import ru.vstu.immovables.ui.items.FromToRange
import ru.vstu.immovables.ui.views.FromToRangeView

/**
 * Created by kkruchinin on 26.11.17.
 */
class FromToRangePresenter : ItemPresenter<FromToRangeView, FromToRange>{
    override fun bindView(view: FromToRangeView, item: FromToRange, position: Int) {
        view.setTitle(item.name)
        view.setDescription(item.description)
    }
}