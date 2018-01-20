package ru.vstu.immovables.ui.property_type

class PropertyChoosePresenterImpl(
        private val items: List<String>,
        private val selectedItem: Int,
        private val view: PropertyChooseView
) : PropertyChoosePresenter {


    override fun onCreate() {
        view.showData(items, selectedItem)
    }

    override fun onResume() {
        view.restoreData()
    }

    override fun onDestroy() {

    }

    override fun onClick(position: Int) {
        view.applySelecting(position)
    }
}