package ru.vstu.immovables.ui.property_type.di

import dagger.Subcomponent
import ru.vstu.immovables.di.PerActivity
import ru.vstu.immovables.ui.property_type.PropertyChooseActivity


@PerActivity
@Subcomponent(modules = arrayOf(PropertyChooseModule::class))
interface PropertyChooseComponent {

    fun inject(activity: PropertyChooseActivity)

}