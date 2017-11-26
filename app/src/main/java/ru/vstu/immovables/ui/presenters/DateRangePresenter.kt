package ru.vstu.immovables.ui.presenters

import com.avito.konveyor.blueprint.ItemPresenter
import ru.vstu.immovables.ui.items.DateRange
import ru.vstu.immovables.ui.views.DateRangeView

/**
 * Created by kkruchinin on 26.11.17.
 */
class DateRangePresenter: ItemPresenter<DateRangeView, DateRange> {
    override fun bindView(view: DateRangeView, item: DateRange, position: Int) {
        view.setTitle(item.name)
        view.setDescription(item.description)
    }
}