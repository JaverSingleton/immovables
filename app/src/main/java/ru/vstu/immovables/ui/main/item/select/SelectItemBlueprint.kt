package ru.vstu.immovables.ui.main.item.select

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.main.item.PropertyItem

class SelectItemBlueprint(
        override val presenter: ItemPresenter<SelectItemView, PropertyItem.Select>
) : ItemBlueprint<SelectItemView, PropertyItem.Select> {
    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.view_selectable_single_line,
            creator = { _, view -> SelectItemView(view) }
    )

    override fun isRelevantItem(item: Item): Boolean = item is PropertyItem.Select
}