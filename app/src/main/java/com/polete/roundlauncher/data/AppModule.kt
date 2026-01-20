package com.polete.roundlauncher.data

import android.content.ComponentName
import android.graphics.drawable.Drawable
import android.os.UserHandle

data class AppModule(
    val label: String,
    val packageName: String,
    val drawable: Drawable?,
    val componentName: ComponentName,
    val user: UserHandle
)