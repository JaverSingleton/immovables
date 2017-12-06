package ru.vstu.immovables.ui.main.blueprints

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.main.holders.DropDownListViewHolder
import ru.vstu.immovables.ui.main.items.Filter
import ru.vstu.immovables.ui.main.views.DropDownListView

/**
 * Created by kkruchinin on 26.11.17.
 */
class DropDownListBlueprint(
        override val presenter: ItemPresenter<DropDownListView, Filter.Chooser>
) : ItemBlueprint<DropDownListView, Filter.Chooser> {
    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.view_item_drop_down_list,
            creator = { _, view -> DropDownListViewHolder(view) }
    )

    override fun isRelevantItem(item: Item): Boolean = item is Filter.Chooser
}