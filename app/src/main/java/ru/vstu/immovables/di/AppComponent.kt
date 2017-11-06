package ru.vstu.immovables.di

import dagger.Component
import ru.vstu.immovables.MainActivity
import ru.vstu.immovables.api.di.ApiModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApiModule::class
))
interface AppComponent {

    fun inject(activity: MainActivity)

}