package ru.vstu.immovables

import android.app.Activity
import android.content.Context
import android.support.annotation.StringRes
import android.view.View
import android.widget.Toast
import ru.vstu.immovables.di.AppComponent
import ru.vstu.immovables.di.ComponentProvider

@Suppress("UNCHECKED_CAST")
val Activity.appComponent: AppComponent
    get() = (application as? ComponentProvider<AppComponent>)
            ?.component
            ?: throw IllegalArgumentException("The application couldn't provide AppComponent")

fun Activity.getContainerView(): View = findViewById(android.R.id.content)