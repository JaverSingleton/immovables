package ru.vstu.immovables.ui.property_type.di

import dagger.Module
import dagger.Provides
import ru.vstu.immovables.di.PerActivity
import ru.vstu.immovables.ui.property_type.PropertyChooseActivity
import ru.vstu.immovables.ui.property_type.PropertyChoosePresenter
import ru.vstu.immovables.ui.property_type.PropertyChoosePresenterImpl
import ru.vstu.immovables.ui.property_type.PropertyChooseView

@Module
class PropertyChooseModule(
        private val propertyChooseActivity: PropertyChooseActivity,
        private val items: List<String>,
        private val selectedItem: Int
) {

    @Provides
    @PerActivity
    fun provideView(): PropertyChooseView =
            propertyChooseActivity

    @Provides
    @PerActivity
    fun providePropertyChoosePresenter(view: PropertyChooseView): PropertyChoosePresenter =
            PropertyChoosePresenterImpl(items, selectedItem, view)

}