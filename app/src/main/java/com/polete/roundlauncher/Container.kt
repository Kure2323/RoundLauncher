package com.polete.roundlauncher

import android.content.Context
import com.polete.roundlauncher.system.cache.AppCache
import com.polete.roundlauncher.system.cache.IconCache
import com.polete.roundlauncher.ui.settingspage.SettingsK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow

object Container {

    // Room
    //lateinit var appRepository: AppRepository

    // Cache
    lateinit var appCache: AppCache
    lateinit var iconCache: IconCache

    // Scope
    lateinit var scope: CoroutineScope

    lateinit var settings: SettingsK


    val appsChangedFlow = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1
    )

    fun init(c: Context) {

        // Settings/SharedPreferences
        settings = SettingsK().loadSettings(c)

        // Cache
        appCache = AppCache(c)
        iconCache = IconCache(c)

        // Scope
        scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)


    }

}