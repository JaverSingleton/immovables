package ru.vstu.immovables.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import ru.vstu.immovables.App
import javax.inject.Singleton

@Singleton
@Component(
        modules = arrayOf(
                AndroidInjectionModule::class,
                AppBuilder::class
        )
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(application: App): Builder
    }

    fun inject(application: App)

}