package ru.vstu.immovables

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import ru.vstu.immovables.di.DaggerAppComponent
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    //    override val component: AppComponent by lazy {
//        DaggerAppComponent.builder().build()
//    }

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

}