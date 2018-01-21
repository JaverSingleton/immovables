package ru.vstu.immovables.ui.report.di

import android.content.Context
import android.os.Bundle
import com.jakewharton.rxrelay2.PublishRelay
import dagger.Module
import dagger.Provides
import ru.vstu.immovables.database.MainDatabase
import ru.vstu.immovables.repository.location.LocationData
import ru.vstu.immovables.repository.report.ReportRepository
import ru.vstu.immovables.repository.report.ReportRepositoryImpl
import ru.vstu.immovables.ui.report.ReportPresenter
import ru.vstu.immovables.ui.report.ReportPresenterImpl


@Module
class ReportModule(
        private val reportId: Long,
        private val presenterState: Bundle?
) {

    private val locationSelectedRelay = PublishRelay.create<LocationData>()

    @Provides
    fun providePresenter(reportRepository: ReportRepository): ReportPresenter =
            ReportPresenterImpl(reportId, reportRepository, presenterState)

    @Provides
    fun provideRepository(database: MainDatabase): ReportRepository =
            ReportRepositoryImpl(database.getReportDao())

}