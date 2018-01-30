package ru.vstu.immovables.ui.history.list.di

import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.SimpleAdapterPresenter
import com.jakewharton.rxrelay2.PublishRelay
import dagger.Module
import dagger.Provides
import ru.vstu.immovables.di.PerFragment
import ru.vstu.immovables.repository.report.ReportRepository
import ru.vstu.immovables.ui.history.list.HistoryListPresenter
import ru.vstu.immovables.ui.history.list.HistoryListPresenterImpl
import ru.vstu.immovables.ui.history.item.HistoryItem
import ru.vstu.immovables.ui.history.item.HistoryItemBlueprint
import ru.vstu.immovables.ui.history.item.HistoryItemPresenter


@Module
class HistoryListModule {

    private val reportSelectedRelay = PublishRelay.create<HistoryItem>()

    @Provides
    fun providePresenter(reportRepository: ReportRepository,
                         adapterPresenter: AdapterPresenter): HistoryListPresenter =
            HistoryListPresenterImpl(reportRepository, adapterPresenter, reportSelectedRelay)

    @Provides
    @PerFragment
    internal fun provideAdapterPresenter(provider: ItemBinder): AdapterPresenter =
            SimpleAdapterPresenter(provider, provider)

    @Provides
    @PerFragment
    internal fun provideItemBinder(historyItemBlueprint: HistoryItemBlueprint): ItemBinder = ItemBinder.Builder()
            .registerItem(historyItemBlueprint)
            .build()

    @Provides
    @PerFragment
    internal fun provideHistoryItemBlueprint(presenter: HistoryItemPresenter): HistoryItemBlueprint =
            HistoryItemBlueprint(presenter)

    @Provides
    @PerFragment
    internal fun provideHistoryItemPresenter(): HistoryItemPresenter =
            HistoryItemPresenter(reportSelectedRelay)

}