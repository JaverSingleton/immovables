package ru.vstu.immovables.ui.main.item.select

import com.avito.konveyor.blueprint.ItemPresenter
import io.reactivex.functions.Consumer
import ru.vstu.immovables.ui.main.item.PropertyItem

class SelectItemPresenter(
        private val clicksConsumer: Consumer<PropertyItem>,
        private val valueChangesConsumer: Consumer<PropertyItem>
) : ItemPresenter<SelectItemView, PropertyItem.Select> {
    override fun bindView(view: SelectItemView, item: PropertyItem.Select, position: Int) {
        view.setValue(item.items.getOrNull(item.selectedItem))
        view.setTitle(item.title)
        view.setClickListener { clicksConsumer.accept(item) }
        view.setClearClickListener {
            item.selectedItem = -1
            view.setValue(null)
            valueChangesConsumer.accept(item)
        }
    }
}