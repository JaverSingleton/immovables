package ru.vstu.immovables.ui.report.di

import dagger.Subcomponent
import ru.vstu.immovables.di.PerActivity
import ru.vstu.immovables.ui.report.ReportActivity


@PerActivity
@Subcomponent(modules = arrayOf(ReportModule::class))
interface ReportComponent {

    fun inject(activity: ReportActivity)

}