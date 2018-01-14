package ru.vstu.immovables.ui.location.item

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import ru.vstu.immovables.R

class LocationSearchBlueprint(
        override val presenter: ItemPresenter<LocationSearchView, LocationSearchItem>
) : ItemBlueprint<LocationSearchView, LocationSearchItem> {
    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.view_item_location_search,
            creator = { _, view -> LocationSearchView(view) }
    )

    override fun isRelevantItem(item: Item): Boolean = item is LocationSearchItem
}