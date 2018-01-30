package ru.vstu.immovables.ui.main.item.location

import com.avito.konveyor.blueprint.ItemPresenter
import io.reactivex.functions.Consumer
import ru.vstu.immovables.ui.main.PropertiesInfoProvider
import ru.vstu.immovables.ui.main.item.Field
import ru.vstu.immovables.ui.view.InfoLevel

class LocationItemPresenter(
        private val propertiesInfoProvider: PropertiesInfoProvider,
        private val clicksConsumer: Consumer<Field>,
        private val valueChangesConsumer: Consumer<Field>
) : ItemPresenter<LocationItemView, Field.Location> {
    override fun bindView(view: LocationItemView, item: Field.Location, position: Int) {
        view.setValue(item.locationData?.name)
        view.setTitle(item.title)
        view.setClickListener { clicksConsumer.accept(item) }
        view.setClearClickListener {
            item.locationData = null
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