package ru.vstu.immovables.ui.location.di

import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.SimpleAdapterPresenter
import com.jakewharton.rxrelay2.PublishRelay
import dagger.Module
import dagger.Provides
import ru.vstu.immovables.di.PerActivity
import ru.vstu.immovables.repository.location.LocationData
import ru.vstu.immovables.repository.location.LocationRepository
import ru.vstu.immovables.repository.location.LocationRepositoryImpl
import ru.vstu.immovables.ui.location.LocationPresenter
import ru.vstu.immovables.ui.location.LocationPresenterImpl
import ru.vstu.immovables.ui.location.item.LocationSearchBlueprint
import ru.vstu.immovables.ui.location.item.LocationSearchPresenter

@Module
class LocationModule(
        private val context: Context,
        private val presenterState: Bundle?
) {

    private val locationSelectedRelay = PublishRelay.create<LocationData>()

    @Provides
    fun providePresenter(locationRepository: LocationRepository,
                         adapterPresenter: AdapterPresenter): LocationPresenter =
            LocationPresenterImpl(locationRepository, locationSelectedRelay, adapterPresenter, presenterState)

    @Provides
    fun provideRepository(): LocationRepository = LocationRepositoryImpl(Geocoder(context))

    @Provides
    @PerActivity
    internal fun provideAdapterPresenter(provider: ItemBinder): AdapterPresenter =
            SimpleAdapterPresenter(provider, provider)

    @Provides
    @PerActivity
    internal fun provideItemBinder(locationSearchBlueprint: LocationSearchBlueprint): ItemBinder = ItemBinder.Builder()
            .registerItem(locationSearchBlueprint)
            .build()

    @Provides
    @PerActivity
    internal fun provideLocationSearchBlueprint(presenter: LocationSearchPresenter): LocationSearchBlueprint {
        return LocationSearchBlueprint(presenter)
    }

    @Provides
    @PerActivity
    internal fun provideLocationSearchPresenter(): LocationSearchPresenter {
        return LocationSearchPresenter(locationSelectedRelay)
    }

}