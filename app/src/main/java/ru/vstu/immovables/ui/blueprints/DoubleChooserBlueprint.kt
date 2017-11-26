package ru.vstu.immovables.ui.blueprints

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.holders.DoubleChooserViewHolder
import ru.vstu.immovables.ui.items.DoubleChooser
import ru.vstu.immovables.ui.views.DoubleChooserView

/**
 * Created by kkruchinin on 26.11.17.
 */
class DoubleChooserBlueprint(
        override val presenter: ItemPresenter<DoubleChooserView, DoubleChooser>
) : ItemBlueprint<DoubleChooserView, DoubleChooser> {
    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.view_item_double_chooser,
            creator = { _, view -> DoubleChooserViewHolder(view) }
    )

    override fun isRelevantItem(item: Item): Boolean = item is DoubleChooser
}