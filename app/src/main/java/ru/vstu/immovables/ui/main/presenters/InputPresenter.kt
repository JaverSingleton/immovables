package ru.vstu.immovables.ui.main.presenters

import com.avito.konveyor.blueprint.ItemPresenter
import ru.vstu.immovables.ui.main.items.Filter
import ru.vstu.immovables.ui.main.views.InputView

/**
 * Created by kkruchinin on 27.11.17.
 */
class InputPresenter: ItemPresenter<InputView, Filter.Input> {
    override fun bindView(view: InputView, item: Filter.Input, position: Int) {
        view.setTitle(item.name)
        view.setDescription(item.description)
        view.setInput(item.input)
    }
}