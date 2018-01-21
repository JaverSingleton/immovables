package ru.vstu.immovables.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import ru.vstu.immovables.App
import ru.vstu.immovables.ui.history.di.HistoryComponent
import ru.vstu.immovables.ui.history.di.HistoryModule
import ru.vstu.immovables.ui.location.di.LocationComponent
import ru.vstu.immovables.ui.location.di.LocationModule
import ru.vstu.immovables.ui.main.di.PropertiesComponent
import ru.vstu.immovables.ui.main.di.PropertiesModule
import ru.vstu.immovables.ui.property_type.di.PropertyChooseComponent
import ru.vstu.immovables.ui.property_type.di.PropertyChooseModule
import ru.vstu.immovables.ui.report.di.ReportComponent
import ru.vstu.immovables.ui.report.di.ReportModule
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

    fun plus(module: LocationModule) : LocationComponent

    fun plus(module: ReportModule) : ReportComponent

    fun plus(module: HistoryModule) : HistoryComponent

    fun plus(module: PropertyChooseModule) : PropertyChooseComponent

    fun plus(module: PropertiesModule) : PropertiesComponent

}