package ru.vstu.immovables.ui.history.maps.di

import dagger.Subcomponent
import ru.vstu.immovables.di.PerFragment
import ru.vstu.immovables.ui.history.maps.HistoryMapsFragment

@PerFragment
@Subcomponent(modules = arrayOf(HistoryMapsModule::class))
interface HistoryMapsComponent {

    fun inject(activity: HistoryMapsFragment)

}
