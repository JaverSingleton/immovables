package ru.vstu.immovables.ui.main.item.number_input

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.main.item.Field

class NumberInputItemBlueprint(
        override val presenter: ItemPresenter<NumberInputItemView, Field.NumberInput>
) : ItemBlueprint<NumberInputItemView, Field.NumberInput> {
    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.view_item_number_input,
            creator = { _, view -> NumberInputItemView(view) }
    )

    override fun isRelevantItem(item: Item): Boolean = item is Field.NumberInput
}