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
import ru.vstu.immovables.ui.main.item.Field
import ru.vstu.immovables.ui.main.item.Property
import java.util.*


class MainPresenterImpl(
        private val view: MainView,
        private val clicks: Observable<Field>,
        private val valueChanges: Observable<Field>,
        private val propertyType: String,
        private val propertiesProvider: PropertiesProvider,
        private val estimateRepository: EstimateRepository
) : MainPresenter {

    companion object {
        const val KEY_ITEMS = "items"
        const val KEY_MORE_BUTTON = "moreButton"
    }

    private var items: List<Field> = listOf()
    private lateinit var moreButton: Field.MoreButton
    private var currentItems: List<Field> = listOf()
    private val disposables = CompositeDisposable()

    override fun onItemSelected(id: Long, selectedValue: Int) {
        val item: Field.Select = getItem(id)
        item.selectedItem = selectedValue
        update(id)
        updatePercent()
    }

    override fun onLocationSelected(id: Long, selectedValue: LocationData) {
        val item: Field.Location = getItem(id)
        item.locationData = selectedValue
        update(id)
        updatePercent()
    }

    override fun onCreate(savedState: Bundle?) {
        items = items.takeIf { it.isNotEmpty() }
                ?: savedState?.getParcelableArrayList<Field>(KEY_ITEMS)
                ?: propertiesProvider.getProperties(propertyType)
        moreButton = savedState?.getParcelable(KEY_MORE_BUTTON)
                ?: Field.MoreButton(Long.MAX_VALUE, false)

        view.showTitle(propertiesProvider.getTitle(propertyType))

        if (items.isEmpty()) {
            view.showNotImplementedPropertyTypeMessage()
            view.close()
        } else {
            updateFields()
        }

        disposables += clicks.subscribeBy(
                onNext = {
                    when (it) {
                        is Field.Select -> {
                            view.selectItem(it.id, it.title, it.items, it.selectedItem)
                        }
                        is Field.Location -> {
                            view.selectLocation(it.id, it.locationData)
                        }
                        is Field.MoreButton -> {
                            it.more =!it.more
                            updateFields()
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
                    estimateRepository.estimate(items.filter { it is Property })
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
        putParcelable(KEY_MORE_BUTTON, moreButton)
    }

    override fun onDestroy() {
        disposables.clear()
    }

    override val more: Boolean get() = moreButton.more

    private fun update(id: Long) {
        view.updateItem(currentItems.indexOfFirst { it.id == id })
    }

    private fun updateFields() {
        currentItems = if (moreButton.more) {
            items
        } else {
            items.filter { it.isMandatory }
        } + moreButton
        view.updateItems(currentItems)
        updatePercent()
    }

    private fun updatePercent() {
        val properties = currentItems.filter { it is Property }.map { it as Property }
        view.showProgress(properties.count { it.hasValue() }.toFloat() / properties.count().toFloat())
        val mandatoryItems = properties.filter { it.isMandatory }
        val isMandatoryItemsFilled = mandatoryItems.count { it.hasValue() } == mandatoryItems.count()
        view.setApplyButtonVisible(isMandatoryItemsFilled)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Field> getItem(id: Long): T = items.first { it.id == id } as T

}