package ru.vstu.immovables.ui.choose_from_list

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import dagger.android.AndroidInjection
import ru.vstu.immovables.R
import javax.inject.Inject

class ChooseActivity : AppCompatActivity(), ChooseView{

    companion object {
        const val REQUEST_CODE = 0

        const val RESULT_CANCEL = 0
        const val RESULT_SUCCESS = 1

        const val EXTRA_ELEMENT_ID = "extraElementId"
        const val EXTRA_TITLE = "extraTitle"
        const val EXTRA_DATA_TO_CHOOSE = "extraData"
        const val EXTRA_CHOOSEN_POSITION = "extraChoosen"
    }

    @Inject
    lateinit var presenter: ChoosePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val title = intent.extras.getString(EXTRA_TITLE)
        val elementId = intent.extras.getLong(EXTRA_ELEMENT_ID)
        val data = intent.extras.getStringArray(EXTRA_DATA_TO_CHOOSE)

        supportActionBar?.title = title

        presenter.onCreate(elementId, data)
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
        val adapter = ChooseAdapter(data, { presenter.onClick(it) })
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        val dividerDecorator = DividerItemDecoration(this, layoutManager.orientation)
        val chooseList: RecyclerView = findViewById(R.id.choose_list)

//        chooseList.addItemDecoration(dividerDecorator)
        chooseList.layoutManager = layoutManager
        chooseList.adapter = adapter

        adapter.notifyDataSetChanged()
    }

    override fun setResultCancel(elementId: Long){
        val intent = Intent()
        intent.putExtra(EXTRA_ELEMENT_ID, elementId)
        setResult(RESULT_CANCEL, intent)
        finish()
    }

    override fun setResultSuccess(elementId: Long, choosenPosition: Int){
        val intent = Intent()
        intent.putExtra(EXTRA_ELEMENT_ID, elementId)
        intent.putExtra(EXTRA_CHOOSEN_POSITION, choosenPosition)
        setResult(RESULT_SUCCESS, intent)
        finish()
    }
}