package ru.vstu.immovables.ui.main.di

import dagger.Subcomponent
import ru.vstu.immovables.di.PerActivity
import ru.vstu.immovables.ui.main.MainActivity

@PerActivity
@Subcomponent(modules = arrayOf(PropertiesModule::class))
interface PropertiesComponent {

    fun inject(activity: MainActivity)

}
