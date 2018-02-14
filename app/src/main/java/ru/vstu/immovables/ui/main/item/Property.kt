package ru.vstu.immovables.ui.main.item

interface Property {

    fun hasValue(): Boolean

    val isMandatory: Boolean

    var info: PropertyInfo?

}