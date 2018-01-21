package ru.vstu.immovables.ui.main.item.number_input

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.main.item.PropertyItem

class NumberInputItemBlueprint(
        override val presenter: ItemPresenter<NumberInputItemView, PropertyItem.NumberInput>
) : ItemBlueprint<NumberInputItemView, PropertyItem.NumberInput> {
    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.view_item_number_input,
            creator = { _, view -> NumberInputItemView(view) }
    )

    override fun isRelevantItem(item: Item): Boolean = item is PropertyItem.NumberInput
}