package ru.vstu.immovables.ui.property_type

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import dagger.android.AndroidInjection
import ru.vstu.immovables.R
import ru.vstu.immovables.ui.main.MainActivity
import javax.inject.Inject

/**
 * Created by Mekamello on 13.12.17.
 */
class PropertyChooseActivity : AppCompatActivity(), PropertyChooseView {

    @Inject
    lateinit var presenter: PropertyChoosePresenter

    private lateinit var adapter: PropertyChooseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property)
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

    override fun showData(data: Array<String>) {
        adapter = PropertyChooseAdapter(data, { presenter.onClick(it) })
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val chooseList: RecyclerView = findViewById(R.id.list)

        chooseList.layoutManager = layoutManager
        chooseList.adapter = adapter
    }

    override fun restoreData() {
        adapter.notifyDataSetChanged()
    }

    override fun openImmovableProperties(propertyType: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.EXTRA_PROPERTY_TYPE, propertyType)
        startActivity(intent)
    }
}