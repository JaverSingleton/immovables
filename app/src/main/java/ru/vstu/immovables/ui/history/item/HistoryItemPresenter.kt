package ru.vstu.immovables.ui.history.item

import com.avito.konveyor.blueprint.ItemPresenter
import io.reactivex.functions.Consumer
import ru.vstu.immovables.toNumberString


class HistoryItemPresenter(
        private val valueConsumer: Consumer<HistoryItem>
) : ItemPresenter<HistoryItemView, HistoryItem> {

    override fun bindView(view: HistoryItemView, item: HistoryItem, position: Int) {
        view.setTitle(item.report.address)
        view.setSubtitle(item.report.cost.toNumberString())
        view.setClickListener { valueConsumer.accept(item) }
    }

}