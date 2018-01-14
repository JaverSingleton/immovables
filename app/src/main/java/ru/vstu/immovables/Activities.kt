package ru.vstu.immovables

import android.app.Activity
import android.view.View
import ru.vstu.immovables.di.AppComponent
import ru.vstu.immovables.di.ComponentProvider

@Suppress("UNCHECKED_CAST")
val Activity.appComponent: AppComponent
    get() = (application as? ComponentProvider<AppComponent>)
            ?.component
            ?: throw IllegalArgumentException("The application couldn't provide AppComponent")

fun Activity.getContainerView(): View = findViewById(android.R.id.content)