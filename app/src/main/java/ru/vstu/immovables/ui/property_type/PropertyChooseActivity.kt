package ru.vstu.immovables.ui.property_type

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import ru.vstu.immovables.R
import ru.vstu.immovables.appComponent
import ru.vstu.immovables.inject
import ru.vstu.immovables.ui.property_type.di.PropertyChooseModule
import javax.inject.Inject

class PropertyChooseActivity : AppCompatActivity(), PropertyChooseView {

    @Inject
    lateinit var presenter: PropertyChoosePresenter

    private lateinit var adapter: PropertyChooseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent
                .plus(PropertyChooseModule(
                        this,
                        intent.getStringArrayListExtra(KEY_ITEMS),
                        intent.getIntExtra(KEY_SELECTED_ITEM, -1)
                ))
                .inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property)
        (findViewById<TextView>(R.id.title)).text = intent.extras.getString(KEY_TITLE)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        presenter.onCreate()
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

    override fun showData(data: List<String>, selectedItem: Int) {
        adapter = PropertyChooseAdapter(data, selectedItem, { presenter.onClick(it) })
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val chooseList: RecyclerView = findViewById(R.id.list)

        chooseList.layoutManager = layoutManager
        chooseList.adapter = adapter
    }

    override fun restoreData() {
        adapter.notifyDataSetChanged()
    }

    override fun applySelecting(selectedItem: Int) {
        setResult(
                Activity.RESULT_OK,
                Intent()
                        .putExtra(KEY_SELECTED_ITEM, selectedItem)
                        .inject(this)
        )
        finish()
    }

    companion object {

        fun Context.propertyChooseScreen(title: String, items: List<String>, selectedItem: Int = -1, id: Long = 0) =
                Intent(this, PropertyChooseActivity::class.java)
                        .putExtra(KEY_TITLE, title)
                        .putStringArrayListExtra(KEY_ITEMS, ArrayList(items))
                        .putExtra(KEY_SELECTED_ITEM, selectedItem)
                        .inject(id)

        fun Intent.extractSelectedItem() = getIntExtra(KEY_SELECTED_ITEM, -1)

        private const val KEY_SELECTED_ITEM = "selectedItem"
        private const val KEY_ITEMS = "items"
        private const val KEY_TITLE = "title"

    }
}