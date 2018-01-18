package ru.vstu.immovables

import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.data_source.ListDataSource

fun <T: Item> AdapterPresenter.updateItems(items: List<T>) {
    onDataSourceChanged(ListDataSource(items))
}