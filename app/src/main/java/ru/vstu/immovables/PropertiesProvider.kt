package ru.vstu.immovables

import android.content.Context
import android.support.annotation.ArrayRes
import ru.vstu.immovables.repository.estimate.Properties
import ru.vstu.immovables.ui.main.item.Field
import ru.vstu.immovables.ui.main.item.PropertyInfo

class PropertiesProvider(private val context: Context) {

    val types: List<String> = getList(R.array.property_type)

    fun getProperties(propertyType: String): List<Field> = when (propertyType) {
        APARTMENT -> apartment()
        else -> listOf()
    }

    fun getTitle(propertyType: String) = when (propertyType) {
        APARTMENT -> context.getString(R.string.Property_Type_Apartment)
        else -> ""
    }

    private fun apartment(): List<Field> = listOf(
            Field.Location(Properties.ADDRESS, "Адрес", isMandatory = true),
            Field.NumberInput(10, "Число собственников", ""),
            Field.Select(40, "Класс жилья", getList(R.array.housing_class)),
            Field.Select(41, "Конструктивно-правовое состояние", getList(R.array.constructive_legal_status)),
            Field.Select(3, "Тип дома", getList(R.array.house_type)),
            Field.Select(4, "Количество комнат в квартире", getList(R.array.room_count), isMandatory = true),
            Field.Photo(42, "Документы на собственность", maxSelectable = 1, info = PropertyInfo("Укажите фото документов, на оцениваемую недвижимость")),
            Field.Photo(43, "Фото недвижимости", info = PropertyInfo("Фотографии внутренней отделки квартиры и планировки")),
            Field.NumberInput(5, "Этажность дома", ""),
            Field.NumberInput(6, "Этаж", "", isMandatory = true),
            Field.NumberInput(Properties.AREA, "Общая площадь квартиры", "", isMandatory = true),
            Field.NumberInput(9, "Размер доли", ""),
            Field.Select(11, "Тип обременения", getList(R.array.encumbrance_type)),
            Field.NumberInput(12, "Жилая площадь", ""),
            Field.NumberInput(13, "Площадь кухни", ""),
            Field.Select(14, "Санузел", getList(R.array.bathroom)),
            Field.Select(15, "Число санузлов", getList(R.array.bathroom), isMandatory = true),
            Field.Select(16, "Балкон", getList(R.array.balcony)),
            Field.Select(17, "Число балконов", getList(R.array.balcony_count)),
            Field.Select(18, "Ремонт", getList(R.array.repairs)),
            Field.NumberInput(19, "Высота потолков", ""),
            Field.Select(20, "Вид из окна", getList(R.array.outer_view)),
            Field.Select(21, "Межкомнатные двери", getList(R.array.common)),
            Field.Select(22, "Мебель", getList(R.array.common)),
            Field.Select(23, "Кухонный гарнитур", getList(R.array.common)),
            Field.Select(24, "Интернет", getList(R.array.common)),
            Field.Select(25, "Телефон", getList(R.array.common)),
            Field.Select(26, "Кабельное ТВ", getList(R.array.common)),
            Field.Select(27, "Кондиционер", getList(R.array.common)),
            Field.NumberInput(28, "Год постройки", ""),
            Field.NumberInput(29, "Год ввода в эксплуатацию", ""),
            Field.Select(30, "Лифт", getList(R.array.lift)),
            Field.Select(31, "Лифт", getList(R.array.lift_count)),
            Field.Select(32, "Парковка", getList(R.array.parking)),
            Field.Select(33, "Консьерж", getList(R.array.common)),
            Field.Select(34, "Охрана", getList(R.array.common)),
            Field.Select(35, "Источники информации", getList(R.array.information_sources)),
            Field.Select(36, "Отопление", getList(R.array.heating)),
            Field.Select(37, "Тип продажи", getList(R.array.sell_type))
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