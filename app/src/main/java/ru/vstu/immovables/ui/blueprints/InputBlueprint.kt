package ru.vstu.immovables.ui.blueprints

import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.holders.InputViewHolder
import ru.vstu.immovables.ui.items.Input
import ru.vstu.immovables.ui.views.InputView

/**
 * Created by kkruchinin on 27.11.17.
 */
class InputBlueprint(
        override val presenter: ItemPresenter<InputView, Input>
) : ItemBlueprint<InputView, Input> {
    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.view_item_input,
            creator = { _, view -> InputViewHolder(view) }
    )

    override fun isRelevantItem(item: Item): Boolean = item is Input
}