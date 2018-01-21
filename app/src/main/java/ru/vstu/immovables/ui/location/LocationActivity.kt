package ru.vstu.immovables.ui.location

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import com.google.android.gms.maps.SupportMapFragment
import ru.vstu.immovables.R
import ru.vstu.immovables.appComponent
import ru.vstu.immovables.getContainerView
import ru.vstu.immovables.inject
import ru.vstu.immovables.repository.location.LocationData
import ru.vstu.immovables.ui.location.di.LocationModule
import ru.vstu.immovables.ui.main.MainActivity
import javax.inject.Inject

class LocationActivity : AppCompatActivity(), LocationPresenter.Router {

    @Inject lateinit var presenter: LocationPresenter
    @Inject lateinit var adapterPresenter: AdapterPresenter
    @Inject lateinit var itemBinder: ItemBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent
                .plus(LocationModule(
                        this,
                        intent.getParcelableExtra(KEY_SELECTED_LOCATION),
                        savedInstanceState?.getBundle(KEY_PRESENTER_STATE)))
                .inject(this)
        setContentView(R.layout.activity_location)
        val view: LocationView = LocationViewImpl(
                getContainerView(),
                supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment,
                adapterPresenter,
                itemBinder
        )
        presenter.attachView(view)
    }

    override fun onStart() {
        super.onStart()
        presenter.attachRouter(this)
    }

    override fun onStop() {
        presenter.detachRouter()
        super.onStop()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putBundle(KEY_PRESENTER_STATE, presenter.onSaveState())
    }

    override fun applySelecting(locationData: LocationData) {
        setResult(
                Activity.RESULT_OK,
                Intent()
                        .putExtra(KEY_SELECTED_LOCATION, locationData)
                        .inject(this)
        )
        finish()
    }

    override fun cancel() {
        finish()
    }

    companion object {

        fun Context.locationSelectingScreen(selectedLocation: LocationData?, id: Long = 0) =
                Intent(this, LocationActivity::class.java)
                        .putExtra(KEY_SELECTED_LOCATION, selectedLocation)
                        .inject(id)

        fun Intent.extractLocation() = getParcelableExtra<LocationData>(KEY_SELECTED_LOCATION)

    }

}

const val KEY_SELECTED_LOCATION = "selectedLocation"
const val KEY_PRESENTER_STATE = "presenterState"
