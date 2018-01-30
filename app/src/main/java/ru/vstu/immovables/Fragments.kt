package ru.vstu.immovables

import android.support.v4.app.Fragment
import ru.vstu.immovables.di.ComponentProvider

@Suppress("UNCHECKED_CAST")
fun <T : Any> Fragment.activityComponent(): T =
        (activity as? ComponentProvider<T>)
                ?.component
                ?: throw IllegalArgumentException("The activity couldn't provide the Component")

@Suppress("UNCHECKED_CAST")
fun <T: Any> Fragment.activityRouter() : T = activity as T