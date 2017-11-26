package ru.vstu.immovables.ui.blueprints

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.holders.DateRangeViewHolder
import ru.vstu.immovables.ui.items.DateRange
import ru.vstu.immovables.ui.views.DateRangeView

/**
 * Created by kkruchinin on 26.11.17.
 */
class DateRangeBlueprint(
        override val presenter: ItemPresenter<DateRangeView, DateRange>
) : ItemBlueprint<DateRangeView, DateRange> {
    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.view_item_date_range,
            creator = { _, view -> DateRangeViewHolder(view) }
    )

    override fun isRelevantItem(item: Item): Boolean = item is DateRange
}