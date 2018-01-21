package ru.vstu.immovables.ui.main.blueprints

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.main.holders.InputViewHolder
import ru.vstu.immovables.ui.main.items.Filter
import ru.vstu.immovables.ui.main.views.InputView

/**
 * Created by kkruchinin on 27.11.17.
 */
class InputBlueprint(
        override val presenter: ItemPresenter<InputView, Filter.Input>
) : ItemBlueprint<InputView, Filter.Input> {
    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.text_input,
            creator = { _, view -> InputViewHolder(view) }
    )

    override fun isRelevantItem(item: Item): Boolean = item is Filter.Input
}