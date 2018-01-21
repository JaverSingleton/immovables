package ru.vstu.immovables

import android.app.Activity
import android.content.Intent


fun Intent.extractId() = getLongExtra(KEY_ID, 0)

fun Intent.inject(activity: Activity): Intent = inject(activity.intent.extractId())

fun Intent.inject(id: Long): Intent = putExtra(KEY_ID, id)

private const val KEY_ID = "id"