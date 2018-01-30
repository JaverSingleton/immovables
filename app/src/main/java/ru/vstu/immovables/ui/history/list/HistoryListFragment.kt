package ru.vstu.immovables.ui.history.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import ru.vstu.immovables.R
import ru.vstu.immovables.activityComponent
import ru.vstu.immovables.activityRouter
import ru.vstu.immovables.ui.history.di.HistoryComponent
import ru.vstu.immovables.ui.history.list.di.HistoryListModule
import javax.inject.Inject

class HistoryListFragment : Fragment() {

    @Inject lateinit var presenter: HistoryListPresenter
    @Inject lateinit var adapterPresenter: AdapterPresenter
    @Inject lateinit var itemBinder: ItemBinder

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_history_list, container, false)
        activityComponent<HistoryComponent>()
                .plus(HistoryListModule())
                .inject(this)
        presenter.attachView(HistoryViewImpl(
                view,
                adapterPresenter,
                itemBinder
        ))
        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.attachRouter(activityRouter())
    }

    override fun onStop() {
        presenter.detachRouter()
        super.onStop()
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

}