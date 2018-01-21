package ru.vstu.immovables

import android.content.Context
import android.support.annotation.ArrayRes
import ru.vstu.immovables.ui.main.item.PropertyItem

class PropertiesProvider(private val context: Context) {

    val types: List<String> = getList(R.array.property_type)

    fun getProperties(propertyType: String): List<PropertyItem> = when (propertyType) {
        APARTMENT -> apartment()
        else -> listOf()
    }

    fun getTitle(propertyType: String) = when (propertyType) {
        APARTMENT -> context.getString(R.string.Property_Type_Apartment)
        else -> ""
    }

    private fun apartment() = listOf(
            PropertyItem.Location(38, "Адрес"),
            PropertyItem.NumberInput(10, "Число собственников", ""),
            PropertyItem.Select(1, "Класс жилья", getList(R.array.housing_class)),
            PropertyItem.Select(2, "Конструктивно-правовое состояние", getList(R.array.constructive_legal_status)),
            PropertyItem.Select(3, "Тип дома", getList(R.array.house_type)),
            PropertyItem.Select(4, "Количество комнат в квартире", getList(R.array.room_count)),
            PropertyItem.NumberInput(5, "Этажность дома", ""),
            PropertyItem.NumberInput(6, "Этаж", ""),
            PropertyItem.NumberInput(7, "Общая площадь квартиры", ""),
            PropertyItem.NumberInput(8, "Ценовой диапазон", ""),
            PropertyItem.NumberInput(9, "Размер доли", ""),
            PropertyItem.Select(11, "Тип обременения", getList(R.array.encumbrance_type)),
            PropertyItem.NumberInput(12, "Жилая площадь", ""),
            PropertyItem.NumberInput(13, "Площадь кухни", ""),
            PropertyItem.Select(14, "Санузел", getList(R.array.bathroom)),
            PropertyItem.Select(15, "Число санузлов", getList(R.array.bathroom)),
            PropertyItem.Select(16, "Балкон", getList(R.array.balcony)),
            PropertyItem.Select(17, "Число балконов", getList(R.array.balcony_count)),
            PropertyItem.Select(18, "Ремонт", getList(R.array.repairs)),
            PropertyItem.NumberInput(19, "Высота потолков", ""),
            PropertyItem.Select(20, "Вид из окна", getList(R.array.outer_view)),
            PropertyItem.Select(21, "Межкомнатные двери", getList(R.array.common)),
            PropertyItem.Select(22, "Мебель", getList(R.array.common)),
            PropertyItem.Select(23, "Кухонный гарнитур", getList(R.array.common)),
            PropertyItem.Select(24, "Интернет", getList(R.array.common)),
            PropertyItem.Select(25, "Телефон", getList(R.array.common)),
            PropertyItem.Select(26, "Кабельное ТВ", getList(R.array.common)),
            PropertyItem.Select(27, "Кондиционер", getList(R.array.common)),
            PropertyItem.NumberInput(28, "Год постройки", ""),
            PropertyItem.NumberInput(29, "Год ввода в эксплуатацию", ""),
            PropertyItem.Select(30, "Лифт", getList(R.array.lift)),
            PropertyItem.Select(31, "Лифт", getList(R.array.lift_count)),
            PropertyItem.Select(32, "Парковка", getList(R.array.parking)),
            PropertyItem.Select(33, "Консьерж", getList(R.array.common)),
            PropertyItem.Select(34, "Охрана", getList(R.array.common)),
            PropertyItem.Select(35, "Источники информации", getList(R.array.information_sources)),
            PropertyItem.Select(36, "Отопление", getList(R.array.heating)),
            PropertyItem.Select(37, "Тип продажи", getList(R.array.sell_type))
    )

    private fun getList(@ArrayRes resource: Int): List<String> =
            context.resources.getStringArray(resource).toList()

}

private const val APARTMENT = "Квартира"
private const val ROOM = "Комната"
private const val HOUSE = "Дом"
private const val STEAD = "Земельный участок"
private const val OFFICE = "Офис"
private const val TRADE_PLACE = "Торговая площадка"
private const val STOCK = "Склад"
private const val FREE_APPOINTMENT = "Помещение свободного назначения"
private const val GARAGE = "Гараж"
private const val BUILDING = "Здание"