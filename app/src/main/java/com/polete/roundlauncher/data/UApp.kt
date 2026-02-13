package com.polete.roundlauncher.data

import android.content.ComponentName
import android.os.UserHandle
import androidx.compose.runtime.Immutable

@Immutable
data class UApp(
    val packageName: String,
    val label: String,
    val componentName: ComponentName,
    val user: UserHandle
)