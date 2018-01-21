package ru.vstu.immovables.ui.history

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.BaseViewHolder
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.avito.konveyor.blueprint.ViewHolderBuilder
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import ru.vstu.immovables.R
import ru.vstu.immovables.hide
import ru.vstu.immovables.show
import ru.vstu.immovables.utils.VerticalDividerDecoration

interface HistoryView {

    fun updateItems()

    fun addClicks(): Observable<Unit>

    fun showNoElements()

    fun showItems()

}

class HistoryViewImpl(
        view: View,
        adapterPresenter: AdapterPresenter,
        viewHolderFactory: ViewHolderBuilder<BaseViewHolder>
) : HistoryView {

    private val context = view.context

    private val addButton: View = view.findViewById(R.id.add_button)

    private val recycler: RecyclerView = view.findViewById(R.id.reports_recycler)

    private val noElements: View = view.findViewById(R.id.no_elements)

    private val toolbar: Toolbar = view.findViewById(R.id.toolbar)

    private val adapter = SimpleRecyclerAdapter(adapterPresenter, viewHolderFactory)

    init {
        toolbar.title = context.getString(R.string.History_Title)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.addItemDecoration(VerticalDividerDecoration.Builder(
                context.getDrawable(R.drawable.divider)
        ).build())
    }

    override fun updateItems() {
        adapter.notifyDataSetChanged()
    }

    override fun addClicks(): Observable<Unit> = addButton.clicks()

    override fun showNoElements() {
        noElements.show()
        recycler.hide()
    }

    override fun showItems() {
        recycler.show()
        noElements.hide()
    }
}