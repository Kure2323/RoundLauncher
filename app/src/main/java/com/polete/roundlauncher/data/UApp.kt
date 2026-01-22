package com.polete.roundlauncher.data

import android.content.ComponentName
import android.graphics.drawable.Drawable
import android.os.UserHandle

data class UApp(
    val packageName: String,
    val label: String,
    val componentName: ComponentName,
    val user: UserHandle
)