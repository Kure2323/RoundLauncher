package com.polete.roundlauncher

import android.app.Application
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.AndroidViewModel
import com.polete.roundlauncher.data.UApp

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val appCache = Container.appCache
    private val iconCache = Container.iconCache

    suspend fun getIcon(uApp: UApp): ImageBitmap {
        return iconCache.getIcon(uApp)
    }

    suspend fun getApps(): List<UApp> {
        return appCache.getApps()
    }


}
