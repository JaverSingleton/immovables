package ru.vstu.immovables.ui.history.list.item

import com.avito.konveyor.blueprint.Item
import ru.vstu.immovables.repository.report.ReportData

class HistoryItem(
        override val id: Long,
        val report: ReportData
) : Item