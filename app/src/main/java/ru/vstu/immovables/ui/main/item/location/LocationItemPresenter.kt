package ru.vstu.immovables.ui.main.item.location

import com.avito.konveyor.blueprint.ItemPresenter
import io.reactivex.functions.Consumer
import ru.vstu.immovables.ui.main.item.PropertyItem

class LocationItemPresenter(
        private val clicksConsumer: Consumer<PropertyItem>,
        private val valueChangesConsumer: Consumer<PropertyItem>
) : ItemPresenter<LocationItemView, PropertyItem.Location> {
    override fun bindView(view: LocationItemView, item: PropertyItem.Location, position: Int) {
        view.setValue(item.locationData?.name)
        view.setTitle(item.title)
        view.setClickListener { clicksConsumer.accept(item) }
        view.setClearClickListener {
            item.locationData = null
            view.setValue(null)
            valueChangesConsumer.accept(item)
        }
    }
}