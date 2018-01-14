package ru.vstu.immovables

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import ru.vstu.immovables.di.AppComponent
import ru.vstu.immovables.di.ComponentProvider
import ru.vstu.immovables.di.DaggerAppComponent
import javax.inject.Inject

class App : Application(), HasActivityInjector, ComponentProvider<AppComponent> {

    override lateinit var component: AppComponent

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
                .application(this)
                .build()
        component.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

}