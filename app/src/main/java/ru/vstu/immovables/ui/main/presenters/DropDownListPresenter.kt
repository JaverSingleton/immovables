package ru.vstu.immovables.ui.main.presenters

import com.avito.konveyor.blueprint.ItemPresenter
import io.reactivex.functions.Consumer
import ru.vstu.immovables.ui.main.items.Filter
import ru.vstu.immovables.ui.main.views.DropDownListView

/**
 * Created by kkruchinin on 26.11.17.
 */
class DropDownListPresenter(private val consumer: Consumer<Filter>): ItemPresenter<DropDownListView, Filter.Chooser> {
    override fun bindView(view: DropDownListView, item: Filter.Chooser, position: Int) {
        view.setTitle(item.name)
        view.setDescription(item.description)

        if(item.choosenField.isNotEmpty()) {
            view.setSelected(item.choosenField)
        } else {
            view.setSelected(item.list[0])
        }

        view.setClickListener { consumer.accept(item) }
    }
}