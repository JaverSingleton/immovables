package ru.vstu.immovables

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.SimpleAdapterPresenter
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import ru.vstu.immovables.ui.blueprints.*
import ru.vstu.immovables.ui.presenters.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycler: RecyclerView = findViewById(R.id.recycler)

        val binder = ItemBinder.Builder()
                .registerItem(createChooserBlueprint())
                .registerItem(createDateRangeBlueprint())
                .registerItem(createDoubleChooserBlueprint())
                .registerItem(createDropDownListBlueprint())
                .registerItem(createFromToRangeBlueprint())
                .build()

        val adapterPresenter = SimpleAdapterPresenter(binder, binder)
        val recyclerAdapter = SimpleRecyclerAdapter(adapterPresenter, binder)
        recycler.adapter = recyclerAdapter

    }

    fun createChooserBlueprint(): ChooserBlueprint = ChooserBlueprint(ChooserPresenter())
    fun createDateRangeBlueprint(): DateRangeBlueprint = DateRangeBlueprint(DateRangePresenter())
    fun createDoubleChooserBlueprint(): DoubleChooserBlueprint = DoubleChooserBlueprint(DoubleChooserPresenter())
    fun createDropDownListBlueprint(): DropDownListBlueprint = DropDownListBlueprint(DropDownListPresenter())
    fun createFromToRangeBlueprint(): FromToRangeBlueprint = FromToRangeBlueprint(FromToRangePresenter())

}
