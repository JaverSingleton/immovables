package ru.vstu.immovables.ui.history.item

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import ru.vstu.immovables.R


class HistoryItemBlueprint(
        override val presenter: ItemPresenter<HistoryItemView, HistoryItem>
) : ItemBlueprint<HistoryItemView, HistoryItem> {
    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.view_item_history,
            creator = { _, view -> HistoryItemView(view) }
    )

    override fun isRelevantItem(item: Item): Boolean = item is HistoryItem
}