package com.polete.roundlauncher

import android.content.Context
import com.polete.roundlauncher.data.local.db.AppDatabase
import com.polete.roundlauncher.data.repo.AppKeyRepo
import com.polete.roundlauncher.system.cache.AppCache
import com.polete.roundlauncher.system.cache.IconCache
import com.polete.roundlauncher.ui.settingspage.SettingsK
import kotlinx.coroutines.flow.MutableSharedFlow

object Container {

    // Cache
    lateinit var appCache: AppCache
    lateinit var iconCache: IconCache

    // BBDD
    lateinit var repo: AppKeyRepo

    lateinit var settings: SettingsK


    val appsChangedFlow = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1
    )

    fun init(c: Context) {

        // BBDD
        repo = AppKeyRepo(AppDatabase.getDatabase(c).dao())

        // Settings/SharedPreferences
        settings = SettingsK().loadSettings(c)

        // Cache
        appCache = AppCache()
        iconCache = IconCache()

    }

}