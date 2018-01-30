package ru.vstu.immovables.ui.main.item.more_button

import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.blueprint.ItemBlueprint
import com.avito.konveyor.blueprint.ItemPresenter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.main.item.Field

class MoreButtonItemBlueprint(
        override val presenter: ItemPresenter<MoreButtonItemView, Field.MoreButton>
) : ItemBlueprint<MoreButtonItemView, Field.MoreButton> {
    override val viewHolderProvider = ViewHolderBuilder.ViewHolderProvider(
            layoutId = R.layout.view_button,
            creator = { _, view -> MoreButtonItemView(view) }
    )

    override fun isRelevantItem(item: Item): Boolean = item is Field.MoreButton
}