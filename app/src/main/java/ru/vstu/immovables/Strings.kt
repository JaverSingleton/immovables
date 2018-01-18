package ru.vstu.immovables

import java.text.NumberFormat


fun Long.toNumberString() = NumberFormat.getNumberInstance().format(this)