package ru.vstu.immovables.ui.property_type

import dagger.Module
import dagger.Provides
import ru.vstu.immovables.Property
import ru.vstu.immovables.di.PerActivity

/**
 * Created by Mekamello on 13.12.17.
 */
@Module
class PropertyChooseModule {

    @Provides
    @PerActivity
    fun provideView(activity: PropertyChooseActivity): PropertyChooseView =
            activity

    @Provides
    @PerActivity
    fun providePropertyChoosePresenter(property: Property, view: PropertyChooseView):PropertyChoosePresenter =
            PropertyChoosePresenterImpl(property, view)

}