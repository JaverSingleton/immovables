package ru.vstu.immovables.ui.main

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.jakewharton.rxbinding2.view.clicks
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.PicassoEngine
import io.reactivex.Observable
import ru.vstu.immovables.*
import ru.vstu.immovables.repository.location.LocationData
import ru.vstu.immovables.repository.report.ReportData
import ru.vstu.immovables.ui.location.LocationActivity.Companion.extractLocation
import ru.vstu.immovables.ui.location.LocationActivity.Companion.locationSelectingScreen
import ru.vstu.immovables.ui.main.di.PropertiesModule
import ru.vstu.immovables.ui.main.item.Field
import ru.vstu.immovables.ui.property_type.PropertyChooseActivity.Companion.extractSelectedItem
import ru.vstu.immovables.ui.property_type.PropertyChooseActivity.Companion.propertyChooseScreen
import ru.vstu.immovables.ui.report.ReportActivity.Companion.reportScreen
import ru.vstu.immovables.utils.VerticalDividerDecoration
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainView {

    @Inject
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var binder: ItemBinder

    @Inject
    lateinit var adapterPresenter: AdapterPresenter

    private lateinit var recyclerAdapter: SimpleRecyclerAdapter

    private lateinit var applyButton: View

    private lateinit var loadingContainer: View
    private lateinit var contentContainer: View
    private lateinit var progressView: ProgressBar
    private lateinit var recycler: RecyclerView

    private var dividerDecoration: RecyclerView.ItemDecoration? = null

    private lateinit var rxPermissions: RxPermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.plus(PropertiesModule(
                this,
                intent.extras.getString(KEY_PROPERTY_TYPE)
        )).inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recycler = findViewById(R.id.recycler)
        recyclerAdapter = SimpleRecyclerAdapter(adapterPresenter, binder)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = recyclerAdapter

        applyButton = findViewById(R.id.apply_button)
        loadingContainer = findViewById(R.id.loading_container)
        contentContainer = findViewById(R.id.content_container)
        progressView = findViewById(R.id.progress)

        hideLoading()

        rxPermissions = RxPermissions(this)
        presenter.onCreate(savedInstanceState?.getBundle(KEY_PRESENTER_STATE))
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putBundle(KEY_PRESENTER_STATE, presenter.onSaveState())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK || data == null) {
            return
        }

        when {
            requestCode == REQ_SELECT_ITEM -> presenter.onItemSelected(
                    data.extractId(),
                    data.extractSelectedItem()
            )
            requestCode == REQ_SELECT_LOCATION -> presenter.onLocationSelected(
                    data.extractId(),
                    data.extractLocation()
            )
            requestCode > REQ_SELECT_PHOTO -> presenter.onPhotoSelected(
                    (requestCode - REQ_SELECT_PHOTO).toLong(),
                    Matisse.obtainResult(data)
            )
            else -> {
                /*Ignore*/
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
                .setMessage(R.string.Dialog_Message)
                .setPositiveButton(R.string.Dialog_Quit) { dialog, _ ->
                    dialog.cancel()
                    finish()
                }
                .setNegativeButton(R.string.Dialog_Cancel) { dialog, _ ->
                    dialog.cancel()
                }
                .show()
    }

    override fun setApplyButtonVisible(visible: Boolean) {
        applyButton.setVisible(visible)
    }

    override fun showTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }


    override fun showNotImplementedPropertyTypeMessage() {
        Toast.makeText(this, R.string.not_implemented_property_type, Toast.LENGTH_SHORT).show()
    }

    override fun close() {
        finish()
    }

    override fun updateItems(items: List<Field>) {
        adapterPresenter.updateItems(items)
        recyclerAdapter.notifyDataSetChanged()

        val padding = resources.getDimensionPixelSize(R.dimen.divider_padding)
        recycler.removeItemDecoration(dividerDecoration)
        dividerDecoration = VerticalDividerDecoration.Builder(getDrawable(R.drawable.divider_and_padding))
                .setPadding(padding, padding)
                .disableDividerForItemAt(items.count() - 1)
                .disableDividerForItemAt(items.count() - 2)
                .build()
        recycler.addItemDecoration(dividerDecoration)
    }

    override fun updateItem(position: Int) {
        recyclerAdapter.notifyItemChanged(position)
    }

    override fun scrollToItem(position: Int) {
        recycler.scrollToPosition(position)
    }

    override fun selectItem(id: Long, title: String, items: List<String>, selectedValue: Int) {
        startActivityForResult(
                propertyChooseScreen(
                        title = title,
                        items = items,
                        selectedItem = selectedValue,
                        id = id
                ),
                REQ_SELECT_ITEM
        )
    }

    override fun selectLocation(id: Long, selectedValue: LocationData?) {
        startActivityForResult(
                locationSelectingScreen(
                        selectedLocation = selectedValue,
                        id = id
                ),
                REQ_SELECT_LOCATION
        )
    }

    override fun selectPhotos(id: Long, selectedValue: List<Uri>, maxSelectable: Int) {
        rxPermissions
                .request(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)
                .subscribe({ granted ->
                    if (granted) {
                        with(Matisse.from(this).choose(setOf(MimeType.JPEG, MimeType.PNG))) {
                            countable(true)
                            maxSelectable(maxSelectable.takeIf { it > 0 } ?: 10)
                            restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            imageEngine(PicassoEngine())
                            forResult(REQ_SELECT_PHOTO + id.toInt())
                        }
                    } else {
                        showToast(R.string.Field_Photo_Permissions)
                    }
                })
    }

    override fun applyClicks(): Observable<Unit> = applyButton.clicks()

    override fun showReport(reportData: ReportData) {
        finish()
        startActivity(reportScreen(reportData.id))
    }

    override fun showLoading() {
        loadingContainer.show()
        contentContainer.hide()
    }

    override fun hideLoading() {
        loadingContainer.hide()
        contentContainer.show()
    }

    override fun showProgress(progress: Float) {
        progressView.progress = (progress * progressView.max).toInt()
    }

    companion object {

        fun Context.propertiesScreen(propertyType: String): Intent = Intent(this, MainActivity::class.java)
                .putExtra(KEY_PROPERTY_TYPE, propertyType)

        private const val KEY_PRESENTER_STATE = "savedPresenterState"
        private const val KEY_PROPERTY_TYPE = "propertyType"
    }

}

private const val REQ_SELECT_ITEM = 0
private const val REQ_SELECT_LOCATION = 1
private const val REQ_SELECT_PHOTO = 10000
