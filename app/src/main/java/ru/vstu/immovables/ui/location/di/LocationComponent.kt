package ru.vstu.immovables.ui.location.di

import dagger.Subcomponent
import ru.vstu.immovables.di.PerActivity
import ru.vstu.immovables.ui.location.LocationActivity

@PerActivity
@Subcomponent(modules = arrayOf(LocationModule::class))
interface LocationComponent {

    fun inject(activity: LocationActivity)

}
