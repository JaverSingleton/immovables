package ru.vstu.immovables

import android.app.Application
import ru.vstu.immovables.di.AppComponent
import ru.vstu.immovables.di.ComponentProvider
import ru.vstu.immovables.di.DaggerAppComponent

class App : Application(), ComponentProvider<AppComponent> {

    override val component: AppComponent by lazy {
        DaggerAppComponent.builder().build()
    }

}