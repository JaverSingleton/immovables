package ru.vstu.immovables.ui.main.item.select

import com.avito.konveyor.blueprint.ItemPresenter
import io.reactivex.functions.Consumer
import ru.vstu.immovables.ui.main.item.PropertyItem
import ru.vstu.immovables.ui.view.InfoLevel

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
        view.setInfo(
                info = item.info?.text ?: item.isMandatory.takeIf { it }?.let { "Обязательное поле" },
                level = item.info?.level ?: item.isMandatory.takeIf { it }?.let { InfoLevel.WARNING } ?: InfoLevel.INFO
        )
    }
}