package ru.vstu.immovables.ui.main.presenters

import com.avito.konveyor.blueprint.ItemPresenter
import ru.vstu.immovables.ui.main.items.Filter
import ru.vstu.immovables.ui.main.views.DateRangeView

/**
 * Created by kkruchinin on 26.11.17.
 */
class DateRangePresenter: ItemPresenter<DateRangeView, Filter.Date> {
    override fun bindView(view: DateRangeView, item: Filter.Date, position: Int) {
        view.setTitle(item.name)
        view.setDescription(item.description)
        view.setFromDate(item.from, { view.openFromDatePicker() })
        view.setToDate(item.to, { view.openToDatePicker() })
    }
}