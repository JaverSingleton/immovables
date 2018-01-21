package ru.vstu.immovables.ui.main

import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.vstu.immovables.PropertiesProvider
import ru.vstu.immovables.repository.estimate.EstimateRepository
import ru.vstu.immovables.repository.location.LocationData
import ru.vstu.immovables.ui.main.item.PropertyItem
import java.util.*


class MainPresenterImpl(
        private val view: MainView,
        private val clicks: Observable<PropertyItem>,
        private val valueChanges: Observable<PropertyItem>,
        private val propertyType: String,
        private val propertiesProvider: PropertiesProvider,
        private val estimateRepository: EstimateRepository
) : MainPresenter {

    companion object {
        const val KEY_ITEMS = "items"
    }

    private var items: List<PropertyItem> = listOf()
    private val disposables = CompositeDisposable()

    override fun onItemSelected(id: Long, selectedValue: Int) {
        val item: PropertyItem.Select = getItem(id)
        item.selectedItem = selectedValue
        update(id)
        updatePercent()
    }

    override fun onLocationSelected(id: Long, selectedValue: LocationData) {
        val item: PropertyItem.Location = getItem(id)
        item.locationData = selectedValue
        update(id)
        updatePercent()
    }

    override fun onCreate(savedState: Bundle?) {
        items = items.takeIf { it.isNotEmpty() }
                ?: savedState?.getParcelableArrayList(KEY_ITEMS)
                ?: propertiesProvider.getProperties(propertyType)

        updatePercent()
        view.showTitle(propertiesProvider.getTitle(propertyType))

        if (items.isEmpty()) {
            view.showNotImplementedPropertyTypeMessage()
            view.close()
        } else {
            view.updateItems(items)
        }

        disposables += clicks.subscribeBy(
                onNext = {
                    when (it) {
                        is PropertyItem.Select -> {
                            view.selectItem(it.id, it.title, it.items, it.selectedItem)
                        }
                        is PropertyItem.Location -> {
                            view.selectLocation(it.id, it.locationData)
                        }
                    }
                }
        )

        disposables += valueChanges.subscribeBy(
                onNext = {
                    updatePercent()
                }
        )

        disposables += view.applyClicks()
                .flatMapSingle {
                    estimateRepository.estimate(items)
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnSubscribe { view.showLoading() }
                            .doAfterTerminate { view.hideLoading() }
                }
                .subscribeBy(
                        onNext = {
                            view.showReport(it)
                        }
                )
    }

    override fun onSaveState(): Bundle = Bundle().apply {
        putParcelableArrayList(KEY_ITEMS, ArrayList(items))
    }

    override fun onDestroy() {
        disposables.clear()
    }

    private fun update(id: Long) {
        view.updateItem(items.indexOfFirst { it.id == id })
    }

    private fun updatePercent() {
        view.showProgress(items.count { it.hasValue() }.toFloat() / items.count().toFloat())
        val mandatoryItems = items.filter { it.isMandatory }
        val isMandatoryItemsFilled = mandatoryItems.count { it.hasValue() } == mandatoryItems.count()
        view.setApplyButtonVisible(isMandatoryItemsFilled)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : PropertyItem> getItem(id: Long): T = items.first { it.id == id } as T

}