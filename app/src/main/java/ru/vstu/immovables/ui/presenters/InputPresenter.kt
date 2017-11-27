package ru.vstu.immovables.ui.presenters

import com.avito.konveyor.blueprint.ItemPresenter
import ru.vstu.immovables.ui.items.Input
import ru.vstu.immovables.ui.views.InputView

/**
 * Created by kkruchinin on 27.11.17.
 */
class InputPresenter: ItemPresenter<InputView, Input> {
    override fun bindView(view: InputView, item: Input, position: Int) {
        view.setTitle(item.name)
        view.setDescription(item.description)
    }
}