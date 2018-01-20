package ru.vstu.immovables.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.vstu.immovables.ui.main.MainActivity
import ru.vstu.immovables.ui.main.MainActivityModule


@Module(includes = arrayOf(AppModule::class))
interface AppBuilder {

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    fun contributeMainActivity(): MainActivity

}