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
        ROOM -> room()
        HOUSE -> house()
        else -> listOf()
    }

    fun getTitle(propertyType: String) = when (propertyType) {
        APARTMENT -> context.getString(R.string.Property_Type_Apartment)
        ROOM -> context.getString(R.string.Property_Type_Room)
        HOUSE -> context.getString(R.string.Property_Type_House)
        else -> ""
    }

    private fun apartment(): List<Field> = listOf(
            Field.Location(Properties.ADDRESS, "Адрес", isMandatory = true),
            Field.NumberInput(10, "Число собственников", ""),
            Field.Select(40, "Класс жилья", getList(R.array.housing_class)),
            Field.Select(41, "Конструктивно-правовое состояние", getList(R.array.constructive_legal_status)),
            Field.Select(3, "Тип дома", getList(R.array.house_type)),
            Field.Select(Properties.ROOMS, "Количество комнат в квартире", getList(R.array.room_count), isMandatory = true),
            Field.Photo(42, "Документы на собственность", maxSelectable = 3, info = PropertyInfo("Укажите фото документов, на оцениваемую недвижимость")),
            Field.Photo(43, "Фото недвижимости", info = PropertyInfo("Фотографии внутренней отделки квартиры и планировки")),
            Field.NumberInput(Properties.MAX_FLOOR, "Этажность дома", ""),
            Field.NumberInput(Properties.FLOOR, "Этаж", ""),
            Field.NumberInput(Properties.AREA, "Общая площадь квартиры, м²", "", isMandatory = true),
            Field.Select(11, "Тип обременения", getList(R.array.encumbrance_type)),
            Field.NumberInput(12, "Жилая площадь, м²", ""),
            Field.NumberInput(13, "Площадь кухни, м²", ""),
            Field.Select(14, "Санузел", getList(R.array.bathroom)),
            Field.Select(15, "Число санузлов", getList(R.array.bathroom)),
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
            Field.Select(30, "Грузовой лифт", getList(R.array.lift)),
            Field.Select(31, "Кол-во лифтов", getList(R.array.lift_count)),
            Field.Select(32, "Парковка", getList(R.array.parking)),
            Field.Select(33, "Консьерж", getList(R.array.common)),
            Field.Select(34, "Охрана", getList(R.array.common)),
            Field.Select(36, "Отопление", getList(R.array.heating)),
            Field.Select(37, "Тип продажи", getList(R.array.sell_type))
    )

    private fun house(): List<Field> = listOf(
            Field.Location(Properties.ADDRESS, "Адрес", isMandatory = true),
            Field.NumberInput(10, "Число собственников", ""),
            Field.Select(40, "Класс жилья", getList(R.array.housing_class)),
            Field.Select(41, "Конструктивно-правовое состояние", getList(R.array.constructive_legal_status)),
            Field.Select(3, "Тип дома", getList(R.array.house_type)),
            Field.Select(Properties.ROOMS, "Количество комнат в доме", getList(R.array.room_count), isMandatory = true),
            Field.Photo(42, "Документы на собственность", maxSelectable = 3, info = PropertyInfo("Укажите фото документов, на оцениваемую недвижимость")),
            Field.Photo(43, "Фото недвижимости", info = PropertyInfo("Фотографии внутренней отделки квартиры и планировки")),
            Field.NumberInput(Properties.MAX_FLOOR, "Этажность дома", ""),
            Field.NumberInput(Properties.AREA, "Общая площадь дома, м²", "", isMandatory = true),
            Field.Select(11, "Тип обременения", getList(R.array.encumbrance_type)),
            Field.NumberInput(12, "Жилая площадь, м²", ""),
            Field.NumberInput(13, "Площадь кухни, м²", ""),
            Field.Select(14, "Санузел", getList(R.array.bathroom)),
            Field.Select(15, "Число санузлов", getList(R.array.bathroom)),
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
            Field.Select(34, "Охрана", getList(R.array.common)),
            Field.Select(36, "Отопление", getList(R.array.heating)),
            Field.Select(37, "Тип продажи", getList(R.array.sell_type))
    )

    private fun room(): List<Field> = listOf(
            Field.Location(Properties.ADDRESS, "Адрес", isMandatory = true),
            Field.NumberInput(10, "Число собственников", ""),
            Field.Select(40, "Класс жилья", getList(R.array.housing_class)),
            Field.Select(3, "Тип дома", getList(R.array.house_type)),
            Field.Photo(43, "Фото недвижимости", info = PropertyInfo("Фотографии внутренней отделки квартиры и планировки")),
            Field.NumberInput(Properties.MAX_FLOOR, "Этажность дома", ""),
            Field.NumberInput(Properties.FLOOR, "Этаж", ""),
            Field.NumberInput(Properties.AREA, "Общая площадь квартиры, м²", "", isMandatory = true),
            Field.Select(11, "Тип обременения", getList(R.array.encumbrance_type)),
            Field.NumberInput(12, "Жилая площадь, м²", ""),
            Field.NumberInput(13, "Площадь кухни, м²", ""),
            Field.Select(14, "Санузел", getList(R.array.bathroom)),
            Field.Select(15, "Число санузлов", getList(R.array.bathroom)),
            Field.Select(18, "Ремонт", getList(R.array.repairs)),
            Field.NumberInput(19, "Высота потолков", ""),
            Field.Select(20, "Вид из окна", getList(R.array.outer_view)),
            Field.Select(21, "Межкомнатные двери", getList(R.array.common)),
            Field.Select(22, "Мебель", getList(R.array.common)),
            Field.Select(23, "Кухонный гарнитур", getList(R.array.common)),
            Field.Select(24, "Интернет", getList(R.array.common)),
            Field.Select(26, "Кабельное ТВ", getList(R.array.common)),
            Field.Select(27, "Кондиционер", getList(R.array.common)),
            Field.Select(30, "Грузовой лифт", getList(R.array.lift)),
            Field.Select(31, "Кол-во лифтов", getList(R.array.lift_count)),
            Field.Select(32, "Парковка", getList(R.array.parking)),
            Field.Select(36, "Отопление", getList(R.array.heating))
    )

    private fun getList(@ArrayRes resource: Int): List<String> =
            context.resources.getStringArray(resource).toList()

}

private const val APARTMENT = "Квартира"
private const val ROOM = "Комната"
private const val HOUSE = "Дом"