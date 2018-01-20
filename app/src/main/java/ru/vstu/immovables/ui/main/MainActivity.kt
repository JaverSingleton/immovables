package ru.vstu.immovables.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.avito.konveyor.data_source.ListDataSource
import dagger.android.AndroidInjection
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.main.items.Filter
import ru.vstu.immovables.ui.property_type.PropertyChooseActivity.Companion.extractId
import ru.vstu.immovables.ui.property_type.PropertyChooseActivity.Companion.extractSelectedItem
import ru.vstu.immovables.ui.property_type.PropertyChooseActivity.Companion.propertyChooseScreen
import ru.vstu.immovables.utils.VerticalDividerDecoration
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

    companion object {
        const val SAVED_PRESENTER_STATE = "savedPresenterState"
        const val EXTRA_PROPERTY_TYPE = "propertyType"
    }

    @Inject
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var binder: ItemBinder

    @Inject
    lateinit var adapterPresenter: AdapterPresenter

    private lateinit var recyclerAdapter: SimpleRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Квартира"

        val propertyType = intent.extras.getString(EXTRA_PROPERTY_TYPE)

        presenter.onCreate(savedInstanceState?.getBundle(SAVED_PRESENTER_STATE), propertyType)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putBundle(SAVED_PRESENTER_STATE, presenter.onSaveState())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_SELECT_ITEM -> presenter.onActivityResult(
                    resultCode,
                    data?.extractId(),
                    data?.extractSelectedItem()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }


    override fun showNotImplementedPropertyTypeMessage() {
        Toast.makeText(this, R.string.not_implemented_property_type, Toast.LENGTH_SHORT).show()
    }

    override fun hide() {
        finish()
    }

    override fun showHousingParameters(propertyFilters: List<Filter>) {
        val recycler: RecyclerView = findViewById(R.id.recycler)
        recyclerAdapter = SimpleRecyclerAdapter(adapterPresenter, binder)

        val padding = resources.getDimensionPixelSize(R.dimen.divider_padding)

        val dividerDecoration = VerticalDividerDecoration.Builder(getDrawable(R.drawable.divider_and_padding))
                .setPadding(padding, padding)
                .build()

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = recyclerAdapter
        recycler.addItemDecoration(dividerDecoration)

        updateHousingParameters(propertyFilters)
    }

    override fun updateHousingParameters(propertyFilters: List<Filter>) {
        adapterPresenter.onDataSourceChanged(ListDataSource(propertyFilters))
        recyclerAdapter.notifyDataSetChanged()
    }

    override fun chooseForResult(title: String, elementId: Long, chooseIn: List<String>, selectedPosition: Int) {
        startActivityForResult(
                propertyChooseScreen(
                        title = title,
                        items = chooseIn,
                        selectedItem = selectedPosition,
                        id = elementId
                ),
                REQ_SELECT_ITEM
        )
    }

}

private const val REQ_SELECT_ITEM = 0
