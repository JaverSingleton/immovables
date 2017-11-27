package ru.vstu.immovables

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.avito.konveyor.ItemBinder
import com.avito.konveyor.adapter.SimpleAdapterPresenter
import com.avito.konveyor.adapter.SimpleRecyclerAdapter
import com.avito.konveyor.blueprint.Item
import com.avito.konveyor.data_source.ListDataSource
import ru.vstu.immovables.ui.blueprints.*
import ru.vstu.immovables.ui.items.*
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
                .registerItem(createInputBlueprint())
                .build()

        val adapterPresenter = SimpleAdapterPresenter(binder, binder)
        val recyclerAdapter = SimpleRecyclerAdapter(adapterPresenter, binder)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = recyclerAdapter

        adapterPresenter.onDataSourceChanged(ListDataSource(getListElements()))
        recyclerAdapter.notifyDataSetChanged()
    }

    private fun createChooserBlueprint(): ChooserBlueprint = ChooserBlueprint(ChooserPresenter())
    private fun createDateRangeBlueprint(): DateRangeBlueprint = DateRangeBlueprint(DateRangePresenter())
    private fun createDoubleChooserBlueprint(): DoubleChooserBlueprint = DoubleChooserBlueprint(DoubleChooserPresenter())
    private fun createDropDownListBlueprint(): DropDownListBlueprint = DropDownListBlueprint(DropDownListPresenter())
    private fun createFromToRangeBlueprint(): FromToRangeBlueprint = FromToRangeBlueprint(FromToRangePresenter())
    private fun createInputBlueprint(): InputBlueprint = InputBlueprint(InputPresenter())

    private fun getListElements(): List<Item> =
            listOf(
                    DropDownList(0, "Тип недвижимости", ""),
                    Chooser(1, "Класс жилья", ""),
                    Chooser(2, "Конструктивно-правовое", "состояние"),
                    DropDownList(3, "Тип дома", ""),
                    Chooser(4, "Количество комнат", "в квартире"),
                    FromToRange(5, "Этажность дома", ""),
                    FromToRange(6, "Этаж", ""),
                    FromToRange(7, "Общая площадь", "квартиры"),
                    FromToRange(8, "Ценовой диапазон", ""),
                    FromToRange(9, "Размер доли", ""),
                    Input(10, "Число собственников", ""),
                    Chooser(11, "Тип обременения", ""),
                    FromToRange(12, "Жилая площадь", ""),
                    FromToRange(13, "Площадь кухни", ""),
                    DoubleChooser(14, "Санузел", ""),
                    DoubleChooser(15, "Балкон", ""),
                    Chooser(16, "Ремонт", ""),
                    FromToRange(17, "Высота потолков", ""),
                    Chooser(18, "Вид из окна", ""),
                    Chooser(19, "Межкомнатные двери", ""),
                    Chooser(20, "Мебель", ""),
                    Chooser(21, "Кухонный гарнитур", ""),
                    Chooser(22, "Интернет", ""),
                    Chooser(23, "Телефон", ""),
                    Chooser(24, "Кабельное ТВ", ""),
                    Chooser(25, "Кондиционер", ""),
                    FromToRange(26, "Год постройки", ""),
                    FromToRange(27, "Год ввода в эксплуатацию", ""),
                    DoubleChooser(28, "Лифт", ""),
                    DropDownList(29, "Парковка", ""),
                    Chooser(30, "Консьерж", ""),
                    Chooser(31, "Охрана", ""),
                    Chooser(32, "Источники информации", ""),
                    Chooser(33, "Отопление", ""),
                    Chooser(34, "Тип продажи", ""),
                    DateRange(35, "Дата подачи объявления", "")
            )

}
