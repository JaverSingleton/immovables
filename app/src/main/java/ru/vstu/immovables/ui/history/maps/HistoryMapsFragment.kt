package ru.vstu.immovables.ui.history.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.SupportMapFragment
import ru.vstu.immovables.activityComponent
import ru.vstu.immovables.activityRouter
import ru.vstu.immovables.ui.history.di.HistoryComponent
import ru.vstu.immovables.ui.history.maps.di.HistoryMapsModule
import javax.inject.Inject


class HistoryMapsFragment : SupportMapFragment() {

    @Inject lateinit var presenter: HistoryMapsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activityComponent<HistoryComponent>()
                .plus(HistoryMapsModule())
                .inject(this)
        presenter.attachView(HistoryMapsViewImpl(this))
        return super.onCreateView(inflater, container, savedInstanceState)
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