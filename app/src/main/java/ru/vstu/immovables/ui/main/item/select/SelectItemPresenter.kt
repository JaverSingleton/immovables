package ru.vstu.immovables.ui.main.item.select

import com.avito.konveyor.blueprint.ItemPresenter
import io.reactivex.functions.Consumer
import ru.vstu.immovables.ui.main.PropertiesInfoProvider
import ru.vstu.immovables.ui.main.item.Field
import ru.vstu.immovables.ui.view.InfoLevel

class SelectItemPresenter(
        private val propertiesInfoProvider: PropertiesInfoProvider,
        private val clicksConsumer: Consumer<Field>,
        private val valueChangesConsumer: Consumer<Field>
) : ItemPresenter<SelectItemView, Field.Select> {
    override fun bindView(view: SelectItemView, item: Field.Select, position: Int) {
        view.setValue(item.items.getOrNull(item.selectedItem))
        view.setTitle(item.title)
        view.setClickListener { clicksConsumer.accept(item) }
        view.setClearClickListener {
            item.selectedItem = -1
            view.setValue(null)
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