package ru.vstu.immovables.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.AdapterPresenter
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.avito.konveyor.data_source.ListDataSource
import dagger.android.AndroidInjection
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.choose_from_list.ChooseActivity
import ru.vstu.immovables.ui.main.items.Filter
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

    companion object {
        const val SAVED_PRESENTER_STATE = "savedPresenterState"
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

        presenter.onCreate(savedInstanceState?.getBundle(SAVED_PRESENTER_STATE))
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putBundle(SAVED_PRESENTER_STATE, presenter.onSaveState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ChooseActivity.REQUEST_CODE -> presenter.onActivityResult(
                    resultCode,
                    data?.extras?.getLong(ChooseActivity.EXTRA_ELEMENT_ID),
                    data?.extras?.getInt(ChooseActivity.EXTRA_CHOOSEN_POSITION)
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

    override fun showNotImplementedPropertyTypeMessage() {
        Toast.makeText(this, R.string.not_implemented_property_type, Toast.LENGTH_SHORT).show()
    }

    override fun showHousingParameters(propertyFilters: List<Filter>) {
        val recycler: RecyclerView = findViewById(R.id.recycler)
        recyclerAdapter = SimpleRecyclerAdapter(adapterPresenter, binder)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = recyclerAdapter

        updateHousingParameters(propertyFilters)
    }

    override fun updateHousingParameters(propertyFilters: List<Filter>) {
        adapterPresenter.onDataSourceChanged(ListDataSource(propertyFilters))
        recyclerAdapter.notifyDataSetChanged()
    }

    override fun chooseForResult(elementId: Long, chooseIn: List<String>) {
        val intent = Intent(this, ChooseActivity::class.java)
        intent.putExtra(ChooseActivity.EXTRA_ELEMENT_ID, elementId)
        intent.putExtra(ChooseActivity.EXTRA_DATA_TO_CHOOSE, chooseIn.toTypedArray())
        startActivityForResult(intent, ChooseActivity.REQUEST_CODE)
    }

}
