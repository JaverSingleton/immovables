package ru.vstu.immovables.ui.main.item.number_input

import com.avito.konveyor.blueprint.ItemPresenter
import io.reactivex.functions.Consumer
import ru.vstu.immovables.ui.main.item.PropertyItem

class NumberInputItemPresenter(
        private val valueChangesConsumer: Consumer<PropertyItem>
) : ItemPresenter<NumberInputItemView, PropertyItem.NumberInput> {
    override fun bindView(view: NumberInputItemView, item: PropertyItem.NumberInput, position: Int) {
        view.setValue(item.value)
        view.setTitle(item.title)
        view.setValueChangedListener {
            item.value = it
            valueChangesConsumer.accept(item)
        }
    }
}