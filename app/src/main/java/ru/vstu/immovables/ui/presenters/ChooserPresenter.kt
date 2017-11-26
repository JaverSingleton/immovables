package ru.vstu.immovables.ui.presenters

import com.avito.konveyor.blueprint.ItemPresenter
import ru.vstu.immovables.ui.items.Chooser
import ru.vstu.immovables.ui.views.ChooserView

/**
 * Created by kkruchinin on 26.11.17.
 */
class ChooserPresenter: ItemPresenter<ChooserView, Chooser> {
    override fun bindView(view: ChooserView, item: Chooser, position: Int) {
        view.setTitle(item.name)
        view.setDescription(item.description)
    }
}