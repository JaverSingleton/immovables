package ru.vstu.immovables.ui.main.item.photo

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.main.item.Field

class PhotoItemBlueprint(
        override val presenter: ItemPresenter<PhotoItemView, Field.Photo>
) : ItemBlueprint<PhotoItemView, Field.Photo> {
    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.view_item_select,
            creator = { _, view -> PhotoItemView(view) }
    )

    override fun isRelevantItem(item: Item): Boolean = item is Field.Photo
}