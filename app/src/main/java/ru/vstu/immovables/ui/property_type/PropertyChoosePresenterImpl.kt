package ru.vstu.immovables.ui.property_type

import ru.vstu.immovables.Property

/**
 * Created by Mekamello on 13.12.17.
 */
class PropertyChoosePresenterImpl(
        private val property: Property,
        private val view: PropertyChooseView
): PropertyChoosePresenter {


    override fun onCreate() {
        view.showData(property.types.toTypedArray())
    }

    override fun onResume() {
        view.restoreData()
    }

    override fun onDestroy() {

    }

    override fun onClick(position: Int) {
        view.openImmovableProperties(property.types[position])
    }
}