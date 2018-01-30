package ru.vstu.immovables.ui.main.item.number_input

import com.avito.konveyor.blueprint.ItemPresenter
import io.reactivex.functions.Consumer
import ru.vstu.immovables.ui.main.PropertiesInfoProvider
import ru.vstu.immovables.ui.main.item.Field
import ru.vstu.immovables.ui.view.InfoLevel

class NumberInputItemPresenter(
        private val propertiesInfoProvider: PropertiesInfoProvider,
        private val valueChangesConsumer: Consumer<Field>
) : ItemPresenter<NumberInputItemView, Field.NumberInput> {
    override fun bindView(view: NumberInputItemView, item: Field.NumberInput, position: Int) {
        view.setValue(item.value)
        view.setTitle(item.title)
        view.setValueChangedListener {
            item.value = it
            valueChangesConsumer.accept(item)
        }
        view.setInfo(
                info = item.info?.text
                        ?: item.isMandatory
                        .takeIf { it && propertiesInfoProvider.more }
                        ?.let { "Обязательное поле" },
                level = item.info?.level
                        ?: item.isMandatory
                        .takeIf { it && propertiesInfoProvider.more }
                        ?.let { InfoLevel.WARNING } ?: InfoLevel.INFO
        )
    }
}