package ru.vstu.immovables.ui.main.presenters

import com.avito.konveyor.blueprint.ItemPresenter
import ru.vstu.immovables.ui.main.items.Filter
import ru.vstu.immovables.ui.main.views.FromToRangeView

/**
 * Created by kkruchinin on 26.11.17.
 */
class FromToRangePresenter : ItemPresenter<FromToRangeView, Filter.Range>{
    override fun bindView(view: FromToRangeView, item: Filter.Range, position: Int) {
        view.setTitle(item.name)
        view.setDescription(item.description)
    }
}