package ru.vstu.immovables.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.vstu.immovables.ui.choose_from_list.ChooseActivity
import ru.vstu.immovables.ui.choose_from_list.ChooseActivityModule
import ru.vstu.immovables.ui.main.MainActivity
import ru.vstu.immovables.ui.main.MainActivityModule


@Module(includes = arrayOf(AppModule::class))
interface AppBuilder {

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(ChooseActivityModule::class))
    fun contributeChooseActivity(): ChooseActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    fun contributeMainActivity(): MainActivity

}