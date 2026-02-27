package com.polete.roundlauncher

import android.content.Context
import com.polete.roundlauncher.system.cache.AppCache
import com.polete.roundlauncher.system.cache.IconCache
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


    val appsChangedFlow = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1
    )

    fun init(c: Context) {

        // Room

        // val db = AppDatabase.getDatabse(c)
        // appRepository = AppRepository(db.appDao)

        // Cache
        appCache = AppCache(c)
        iconCache = IconCache(c)

        // Scope
        scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)


    }

}