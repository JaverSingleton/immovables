package ru.vstu.immovables.ui.history.list

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import ru.vstu.immovables.R
import ru.vstu.immovables.hide
import ru.vstu.immovables.show
import ru.vstu.immovables.utils.VerticalDividerDecoration

interface HistoryListView {

    fun updateItems()

    fun showNoElements()

    fun showItems()

}

class HistoryListViewImpl(
        view: View,
        adapterPresenter: AdapterPresenter,
        viewHolderFactory: ViewHolderBuilder<BaseViewHolder>
) : HistoryListView {

    private val context = view.context

    private val recycler: RecyclerView = view.findViewById(R.id.reports_recycler)

    private val noElements: View = view.findViewById(R.id.no_elements)

    private val adapter = SimpleRecyclerAdapter(adapterPresenter, viewHolderFactory)

    init {
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.addItemDecoration(VerticalDividerDecoration.Builder(
                context.getDrawable(R.drawable.divider)
        ).build())
    }

    override fun updateItems() {
        adapter.notifyDataSetChanged()
    }

    override fun showNoElements() {
        noElements.show()
        recycler.hide()
    }

    override fun showItems() {
        recycler.show()
        noElements.hide()
    }
}