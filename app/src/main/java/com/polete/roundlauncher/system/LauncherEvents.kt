package com.polete.roundlauncher.system

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

object LauncherEvents {

    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

}