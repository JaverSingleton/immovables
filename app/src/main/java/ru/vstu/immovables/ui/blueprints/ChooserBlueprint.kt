package ru.vstu.immovables.ui.blueprints

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.holders.ChooserViewHolder
import ru.vstu.immovables.ui.items.Chooser
import ru.vstu.immovables.ui.views.ChooserView

/**
 * Created by kkruchinin on 26.11.17.
 */
class ChooserBlueprint(
        override val presenter: ItemPresenter<ChooserView, Chooser>
) : ItemBlueprint<ChooserView, Chooser> {
    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.view_item_chooser,
            creator = { _, view -> ChooserViewHolder(view) }
    )

    override fun isRelevantItem(item: Item): Boolean = item is Chooser
}