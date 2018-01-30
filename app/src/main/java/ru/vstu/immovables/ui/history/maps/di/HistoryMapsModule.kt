package ru.vstu.immovables.ui.history.maps.di

import com.avito.konveyor.adapter.AdapterPresenter
import dagger.Module
import dagger.Provides
import ru.vstu.immovables.repository.report.ReportRepository
import ru.vstu.immovables.ui.history.maps.HistoryMapsPresenter
import ru.vstu.immovables.ui.history.maps.HistoryMapsPresenterImpl


@Module
class HistoryMapsModule {

    @Provides
    fun providePresenter(reportRepository: ReportRepository): HistoryMapsPresenter =
            HistoryMapsPresenterImpl(reportRepository)

}