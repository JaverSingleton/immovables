package ru.vstu.immovables.ui.history.di

import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.SimpleAdapterPresenter
import com.jakewharton.rxrelay2.PublishRelay
import dagger.Module
import dagger.Provides
import ru.vstu.immovables.database.MainDatabase
import ru.vstu.immovables.di.PerActivity
import ru.vstu.immovables.repository.report.ReportRepository
import ru.vstu.immovables.repository.report.ReportRepositoryImpl
import ru.vstu.immovables.ui.history.HistoryPresenter
import ru.vstu.immovables.ui.history.HistoryPresenterImpl
import ru.vstu.immovables.ui.history.item.HistoryItem
import ru.vstu.immovables.ui.history.item.HistoryItemBlueprint
import ru.vstu.immovables.ui.history.item.HistoryItemPresenter


@Module
class HistoryModule() {

    private val reportSelectedRelay = PublishRelay.create<HistoryItem>()

    @Provides
    fun providePresenter(reportRepository: ReportRepository,
                         adapterPresenter: AdapterPresenter): HistoryPresenter =
            HistoryPresenterImpl(reportRepository, adapterPresenter, reportSelectedRelay)

    @Provides
    @PerActivity
    internal fun provideAdapterPresenter(provider: ItemBinder): AdapterPresenter =
            SimpleAdapterPresenter(provider, provider)

    @Provides
    @PerActivity
    internal fun provideItemBinder(historyItemBlueprint: HistoryItemBlueprint): ItemBinder = ItemBinder.Builder()
            .registerItem(historyItemBlueprint)
            .build()

    @Provides
    @PerActivity
    internal fun provideHistoryItemBlueprint(presenter: HistoryItemPresenter): HistoryItemBlueprint =
            HistoryItemBlueprint(presenter)

    @Provides
    @PerActivity
    internal fun provideHistoryItemPresenter(): HistoryItemPresenter =
            HistoryItemPresenter(reportSelectedRelay)

}