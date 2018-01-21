package ru.vstu.immovables.ui.main.item.location

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.main.item.PropertyItem

class LocationItemBlueprint(
        override val presenter: ItemPresenter<LocationItemView, PropertyItem.Location>
) : ItemBlueprint<LocationItemView, PropertyItem.Location> {
    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.view_selectable_single_line,
            creator = { _, view -> LocationItemView(view) }
    )

    override fun isRelevantItem(item: Item): Boolean = item is PropertyItem.Location
}