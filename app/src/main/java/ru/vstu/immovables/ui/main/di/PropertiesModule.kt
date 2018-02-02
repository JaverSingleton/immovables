package ru.vstu.immovables.ui.main.di

import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.SimpleAdapterPresenter
import com.jakewharton.rxrelay2.PublishRelay
import dagger.Module
import dagger.Provides
import ru.vstu.immovables.PropertiesProvider
import ru.vstu.immovables.di.PerActivity
import ru.vstu.immovables.repository.estimate.EstimateRepository
import ru.vstu.immovables.ui.main.MainActivity
import ru.vstu.immovables.ui.main.MainPresenter
import ru.vstu.immovables.ui.main.MainPresenterImpl
import ru.vstu.immovables.ui.main.MainView
import ru.vstu.immovables.ui.main.item.Field
import ru.vstu.immovables.ui.main.item.location.LocationItemBlueprint
import ru.vstu.immovables.ui.main.item.location.LocationItemPresenter
import ru.vstu.immovables.ui.main.item.more_button.MoreButtonItemBlueprint
import ru.vstu.immovables.ui.main.item.more_button.MoreButtonItemPresenter
import ru.vstu.immovables.ui.main.item.number_input.NumberInputItemBlueprint
import ru.vstu.immovables.ui.main.item.number_input.NumberInputItemPresenter
import ru.vstu.immovables.ui.main.item.photo.PhotoItemBlueprint
import ru.vstu.immovables.ui.main.item.photo.PhotoItemPresenter
import ru.vstu.immovables.ui.main.item.select.SelectItemBlueprint
import ru.vstu.immovables.ui.main.item.select.SelectItemPresenter

@Module
class PropertiesModule(
        private val activity: MainActivity,
        private val propertyType: String
) {

    private val clicks: PublishRelay<Field> = PublishRelay.create()
    private val valueChanges: PublishRelay<Field> = PublishRelay.create()

    @Provides
    @PerActivity
    fun provideMainView(): MainView = activity

    @Provides
    @PerActivity
    fun provideMainPresenter(mainView: MainView,
                             propertiesProvider: PropertiesProvider,
                             estimateRepository: EstimateRepository): MainPresenter =
            MainPresenterImpl(
                    mainView,
                    clicks,
                    valueChanges,
                    propertyType,
                    propertiesProvider,
                    estimateRepository
            )

    @Provides
    @PerActivity
    fun provideBinder(presenter: MainPresenter): ItemBinder =
            ItemBinder.Builder()
                    .registerItem(SelectItemBlueprint(SelectItemPresenter(presenter, clicks, valueChanges)))
                    .registerItem(LocationItemBlueprint(LocationItemPresenter(presenter, clicks, valueChanges)))
                    .registerItem(NumberInputItemBlueprint(NumberInputItemPresenter(presenter, valueChanges)))
                    .registerItem(MoreButtonItemBlueprint(MoreButtonItemPresenter(clicks)))
                    .registerItem(PhotoItemBlueprint(PhotoItemPresenter(presenter, clicks, valueChanges)))
                    .build()

    @Provides
    @PerActivity
    fun provideAdapterPresenter(binder: ItemBinder): AdapterPresenter = SimpleAdapterPresenter(binder, binder)

}