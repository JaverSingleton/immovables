package ru.vstu.immovables.ui.history.list.di

import dagger.Subcomponent
import ru.vstu.immovables.di.PerFragment
import ru.vstu.immovables.ui.history.list.HistoryListFragment

@PerFragment
@Subcomponent(modules = arrayOf(HistoryListModule::class))
interface HistoryListComponent {

    fun inject(activity: HistoryListFragment)

}
