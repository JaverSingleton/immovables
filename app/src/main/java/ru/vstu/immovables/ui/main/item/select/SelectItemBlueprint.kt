package ru.vstu.immovables.ui.main.item.select

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.main.item.Field

class SelectItemBlueprint(
        override val presenter: ItemPresenter<SelectItemView, Field.Select>
) : ItemBlueprint<SelectItemView, Field.Select> {
    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.view_item_select,
            creator = { _, view -> SelectItemView(view) }
    )

    override fun isRelevantItem(item: Item): Boolean = item is Field.Select
}