package ru.vstu.immovables.ui.history.di

import dagger.Subcomponent
import ru.vstu.immovables.di.PerActivity
import ru.vstu.immovables.ui.history.HistoryActivity
import ru.vstu.immovables.ui.history.list.di.HistoryListComponent
import ru.vstu.immovables.ui.history.list.di.HistoryListModule
import ru.vstu.immovables.ui.history.maps.di.HistoryMapsComponent
import ru.vstu.immovables.ui.history.maps.di.HistoryMapsModule

@PerActivity
@Subcomponent(modules = arrayOf(HistoryModule::class))
interface HistoryComponent {

    fun inject(activity: HistoryActivity)

    fun plus(module: HistoryListModule) : HistoryListComponent

    fun plus(module: HistoryMapsModule) : HistoryMapsComponent

}
