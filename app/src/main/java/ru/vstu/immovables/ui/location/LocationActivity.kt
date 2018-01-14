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
import ru.vstu.immovables.repository.LocationData
import ru.vstu.immovables.ui.location.di.LocationModule
import javax.inject.Inject

class LocationActivity : AppCompatActivity(), LocationPresenter.Router {

    @Inject lateinit var presenter: LocationPresenter
    @Inject lateinit var adapterPresenter: AdapterPresenter
    @Inject lateinit var itemBinder: ItemBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent
                .plus(LocationModule(this, savedInstanceState?.getBundle(KEY_PRESENTER_STATE)))
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

    override fun applySelecting(locationData: LocationData) {
        setResult(
                Activity.RESULT_OK,
                Intent().putExtra(KEY_SELECTED_LOCATION, locationData)
        )
        finish()
    }

    override fun cancel() {
        finish()
    }

    companion object {

        fun Context.selectLocation(selectedLocation: LocationData?) =
                Intent(this, LocationActivity::class.java)
                        .putExtra(KEY_SELECTED_LOCATION, selectedLocation)

        fun Intent.extractLocation() = getParcelableExtra<LocationData>(KEY_SELECTED_LOCATION)

    }

}

const val KEY_SELECTED_LOCATION = "selectedLocation"
const val KEY_PRESENTER_STATE = "presenterState"
