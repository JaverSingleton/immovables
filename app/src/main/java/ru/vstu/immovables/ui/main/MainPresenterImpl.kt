package ru.vstu.immovables.ui.main

import android.net.Uri
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import ru.vstu.immovables.PropertiesProvider
import ru.vstu.immovables.repository.estimate.EstimateRepository
import ru.vstu.immovables.repository.estimate.IncorrectDataException
import ru.vstu.immovables.repository.estimate.Properties.Companion.AREA
import ru.vstu.immovables.repository.estimate.Properties.Companion.KITCHEN_AREA
import ru.vstu.immovables.repository.estimate.Properties.Companion.LIVING_AREA
import ru.vstu.immovables.repository.location.LocationData
import ru.vstu.immovables.ui.main.item.Field
import ru.vstu.immovables.ui.main.item.Property
import ru.vstu.immovables.ui.main.item.PropertyInfo
import ru.vstu.immovables.ui.view.InfoLevel
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

    override fun onPhotoSelected(id: Long, selectedValue: List<Uri>) {
        val item: Field.Photo = getItem(id)
        item.photos = selectedValue
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
                            it.more = !it.more
                            updateFields()
                        }
                        is Field.Photo -> {
                            view.selectPhotos(it.id, it.photos, it.maxSelectable)
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
                            .doOnSubscribe {
                                items.filterIsInstance(Property::class.java)
                                        .filter { it.info?.level == InfoLevel.ERROR }
                                        .forEach { it.info = null }
                                updateFields()
                                view.showLoading()
                            }
                            .doAfterTerminate { view.hideLoading() }
                            .onErrorResumeNext { error ->
                                if (error is IncorrectDataException) {
                                    error.errors.forEach {
                                        val item: Field.NumberInput = getItem(it.key)
                                        item.info = PropertyInfo(it.value, InfoLevel.ERROR)
                                        update(it.key)
                                        scrollTo(it.key)
                                    }
                                }
                                Single.never()
                            }
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

    override val area: Float get() = findItem<Field.NumberInput>(AREA)?.value?.toFloatOrNull() ?: 0f

    override val kitchenArea: Float get() = findItem<Field.NumberInput>(KITCHEN_AREA)?.value?.toFloatOrNull() ?: 0f

    override val livingArea: Float get() = findItem<Field.NumberInput>(LIVING_AREA)?.value?.toFloatOrNull() ?: 0f

    private fun update(id: Long) {
        view.updateItem(currentItems.indexOfFirst { it.id == id })
    }

    private fun scrollTo(id: Long) {
        view.scrollToItem(currentItems.indexOfFirst { it.id == id })
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

    @Suppress("UNCHECKED_CAST")
    private fun <T : Field> findItem(id: Long): T? = items.firstOrNull { it.id == id } as? T?

}