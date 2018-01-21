package ru.vstu.immovables.ui.report.di

import android.os.Bundle
import com.jakewharton.rxrelay2.PublishRelay
import dagger.Module
import dagger.Provides
import ru.vstu.immovables.repository.location.LocationData
import ru.vstu.immovables.repository.report.ReportRepository
import ru.vstu.immovables.ui.report.ReportPresenter
import ru.vstu.immovables.ui.report.ReportPresenterImpl


@Module
class ReportModule(
        private val reportId: Long,
        private val presenterState: Bundle?
) {

    @Provides
    fun providePresenter(reportRepository: ReportRepository): ReportPresenter =
            ReportPresenterImpl(reportId, reportRepository, presenterState)

}