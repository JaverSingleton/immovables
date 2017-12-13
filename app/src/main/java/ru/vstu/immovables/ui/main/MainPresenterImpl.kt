package ru.vstu.immovables.ui.main

import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import ru.vstu.immovables.Property
import ru.vstu.immovables.ui.main.items.Filter
import java.util.*


class MainPresenterImpl(
        private val view: MainView,
        private val clicks: Observable<Filter>,
        private val property: Property
) : MainPresenter {

    companion object {
        const val INVALID = -1

        const val SAVED_PROPERTY_TYPE = "propertyType"
        const val SAVED_ITEM_LIST = "itemList"

        const val APARTMENT = "Квартира"
        const val ROOM = "Комната"
        const val HOUSE = "Дом"
        const val STEAD = "Земельный участок"
        const val OFFICE = "Офис"
        const val TRADE_PLACE = "Торговая площадка"
        const val STOCK = "Склад"
        const val FREE_APPOINTMENT = "Помещение свободного назначения"
        const val GARAGE = "Гараж"
        const val BUILDING = "Здание"
    }

    var propertyType: String? = null
    var filters: List<Filter> = listOf()
    val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable.add(
                clicks.subscribeBy(
                        onNext = { chooseFromList(it) }
                )
        )
    }

    override fun onCreate(savedState: Bundle?, propertyType: String?) {
        if (savedState == null && propertyType == null) {
            view.hide()
        }

        this.propertyType = propertyType

        if (savedState != null && propertyType == null) {
            this.propertyType = savedState.getString(SAVED_PROPERTY_TYPE)
            val savedItems: ArrayList<Filter> = savedState.getParcelableArrayList(SAVED_ITEM_LIST) ?: arrayListOf()

            if (this.propertyType == null || savedItems.isEmpty()) {
                view.hide()
            } else {
                view.showHousingParameters(savedItems)
            }
        }

        if(propertyType != null){
            val position = property.types.indexOf(propertyType)
            performItemList(position)
        }
    }

    override fun onSaveState(): Bundle {
        val state = Bundle()
        state.putString(SAVED_PROPERTY_TYPE, propertyType)
        state.putParcelableArrayList(SAVED_ITEM_LIST, ArrayList(filters))
        return state
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    override fun onActivityResult(requestCode: Int, elementId: Long?, choosenPosition: Int?) {
        if (choosenPosition != null) {
            when (elementId) {
                INVALID.toLong() -> performItemList(choosenPosition)
                else -> {
                    val filter = filters.findLast { it.id == elementId }
                    if (filter is Filter.Chooser) {
                        filter.choosenField = filter.list[choosenPosition]
                    }
                    view.updateHousingParameters(filters)
                }

            }
        }

    }

    private fun performItemList(choosenPosition: Int) = when (property.types[choosenPosition]) {
        APARTMENT -> {
            filters = property.apartment()
            view.showHousingParameters(filters)
        }
        else -> {
            view.showNotImplementedPropertyTypeMessage()
            view.hide()
        }
    }

    private fun chooseFromList(filter: Filter) {
        if (filter is Filter.Chooser) {
            view.chooseForResult(filter.id, filter.list)
        }
    }
}