package ru.vstu.immovables

import android.content.Context
import android.support.annotation.ArrayRes
import ru.vstu.immovables.ui.main.items.Filter

class Property(private val context: Context) {

    val types: List<String> = getList(R.array.property_type)

    fun apartment(): List<Filter> = listOf(
            Filter.Input(10, "Число собственников", ""),
            Filter.Chooser(1, "Класс жилья", "", getList(R.array.housing_class)),
            Filter.Chooser(2, "Конструктивно-правовое состояние", "", getList(R.array.constructive_legal_status)),
            Filter.Chooser(3, "Тип дома", "", getList(R.array.house_type)),
            Filter.Chooser(4, "Количество комнат в квартире", "", getList(R.array.room_count)),
            Filter.Range(5, "Этажность дома", ""),
            Filter.Range(6, "Этаж", ""),
            Filter.Range(7, "Общая площадь квартиры", ""),
            Filter.Range(8, "Ценовой диапазон", ""),
            Filter.Range(9, "Размер доли", ""),
            Filter.Chooser(11, "Тип обременения", "", getList(R.array.encumbrance_type)),
            Filter.Range(12, "Жилая площадь", ""),
            Filter.Range(13, "Площадь кухни", ""),
            Filter.Chooser(14, "Санузел", "", getList(R.array.bathroom)),
            Filter.Chooser(15, "Число санузлов", "", getList(R.array.bathroom)),
            Filter.Chooser(16, "Балкон", "", getList(R.array.balcony)),
            Filter.Chooser(17, "Число балконов", "", getList(R.array.balcony_count)),
            Filter.Chooser(18, "Ремонт", "", getList(R.array.repairs)),
            Filter.Range(19, "Высота потолков", ""),
            Filter.Chooser(20, "Вид из окна", "", getList(R.array.outer_view)),
            Filter.Chooser(21, "Межкомнатные двери", "", getList(R.array.common)),
            Filter.Chooser(22, "Мебель", "", getList(R.array.common)),
            Filter.Chooser(23, "Кухонный гарнитур", "", getList(R.array.common)),
            Filter.Chooser(24, "Интернет", "", getList(R.array.common)),
            Filter.Chooser(25, "Телефон", "", getList(R.array.common)),
            Filter.Chooser(26, "Кабельное ТВ", "", getList(R.array.common)),
            Filter.Chooser(27, "Кондиционер", "", getList(R.array.common)),
            Filter.Range(28, "Год постройки", ""),
            Filter.Range(29, "Год ввода в эксплуатацию", ""),
            Filter.Chooser(30, "Лифт", "", getList(R.array.lift)),
            Filter.Chooser(31, "Лифт", "", getList(R.array.lift_count)),
            Filter.Chooser(32, "Парковка", "", getList(R.array.parking)),
            Filter.Chooser(33, "Консьерж", "", getList(R.array.common)),
            Filter.Chooser(34, "Охрана", "", getList(R.array.common)),
            Filter.Chooser(35, "Источники информации", "", getList(R.array.information_sources)),
            Filter.Chooser(36, "Отопление", "", getList(R.array.heating)),
            Filter.Chooser(37, "Тип продажи", "", getList(R.array.sell_type)),
            Filter.Date(38, "Дата подачи объявления", "")
    )

    private fun getList(@ArrayRes resource: Int): List<String> =
            context.resources.getStringArray(resource).toList()
}
