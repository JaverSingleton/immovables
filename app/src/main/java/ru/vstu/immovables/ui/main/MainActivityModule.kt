package ru.vstu.immovables.ui.main

import android.content.Context
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.SimpleAdapterPresenter
import com.jakewharton.rxrelay2.PublishRelay
import dagger.Module
import dagger.Provides
import ru.vstu.immovables.Property
import ru.vstu.immovables.di.PerActivity
import ru.vstu.immovables.ui.main.blueprints.DateRangeBlueprint
import ru.vstu.immovables.ui.main.blueprints.DropDownListBlueprint
import ru.vstu.immovables.ui.main.blueprints.FromToRangeBlueprint
import ru.vstu.immovables.ui.main.blueprints.InputBlueprint
import ru.vstu.immovables.ui.main.items.Filter
import ru.vstu.immovables.ui.main.presenters.DateRangePresenter
import ru.vstu.immovables.ui.main.presenters.DropDownListPresenter
import ru.vstu.immovables.ui.main.presenters.FromToRangePresenter
import ru.vstu.immovables.ui.main.presenters.InputPresenter

@Module
class MainActivityModule {

    private val clicks: PublishRelay<Filter> = PublishRelay.create()

    @Provides
    @PerActivity
    fun provideMainView(view: MainActivity): MainView = view

    @Provides
    @PerActivity
    fun provideProperty(context: Context): Property = Property(context)

    @Provides
    @PerActivity
    fun provideMainPresenter(mainView: MainView, property: Property): MainPresenter =
            MainPresenterImpl(mainView, clicks, property)

    @Provides
    @PerActivity
    fun provideBinder(): ItemBinder =
            ItemBinder.Builder()
                    .registerItem(DateRangeBlueprint(DateRangePresenter()))
                    .registerItem(DropDownListBlueprint(DropDownListPresenter(clicks)))
                    .registerItem(FromToRangeBlueprint(FromToRangePresenter()))
                    .registerItem(InputBlueprint(InputPresenter()))
                    .build()

    @Provides
    @PerActivity
    fun provideAdapterPresenter(binder: ItemBinder): AdapterPresenter = SimpleAdapterPresenter(binder, binder)

}