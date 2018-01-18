package ru.vstu.immovables


fun <T : Any, V : Any> use(first: T?, second: V?, func: (T, V) -> Unit) {
    first ?: return
    second ?: return
    func(first, second)
}

fun <T : Any, V : Any, E : Any> use(first: T?, second: V?, third: E?, func: (T, V, E) -> Unit) {
    first ?: return
    second ?: return
    third ?: return
    func(first, second, third)
}