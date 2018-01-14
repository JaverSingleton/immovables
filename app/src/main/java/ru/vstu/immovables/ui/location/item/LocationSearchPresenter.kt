package ru.vstu.immovables.ui.location.item

import com.avito.konveyor.blueprint.ItemPresenter
import io.reactivex.functions.Consumer
import ru.vstu.immovables.repository.LocationData

class LocationSearchPresenter(
        private val valueConsumer: Consumer<LocationData>
) : ItemPresenter<LocationSearchView, LocationSearchItem> {
    override fun bindView(view: LocationSearchView, item: LocationSearchItem, position: Int) {
        view.setValue(item.location.name)
        view.setClickListener { valueConsumer.accept(item.location) }
    }
}