package ru.vstu.immovables.ui.choose_from_list

import dagger.Module
import dagger.Provides
import ru.vstu.immovables.di.PerActivity

@Module
class ChooseActivityModule {

    @Provides
    @PerActivity
    fun provideChooseView(view: ChooseActivity): ChooseView = view

    @Provides
    @PerActivity
    fun provideChoosePresenter(view: ChooseView): ChoosePresenter = ChoosePresenterImpl(view)

}