package ru.vstu.immovables.ui.blueprints

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.holders.FromToRangeViewHolder
import ru.vstu.immovables.ui.items.FromToRange
import ru.vstu.immovables.ui.views.FromToRangeView

/**
 * Created by kkruchinin on 26.11.17.
 */
class FromToRangeBlueprint(
        override val presenter: ItemPresenter<FromToRangeView, FromToRange>
) : ItemBlueprint<FromToRangeView, FromToRange> {
    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.view_item_from_to_range,
            creator = { _, view -> FromToRangeViewHolder(view) }
    )

    override fun isRelevantItem(item: Item): Boolean = item is FromToRange
}