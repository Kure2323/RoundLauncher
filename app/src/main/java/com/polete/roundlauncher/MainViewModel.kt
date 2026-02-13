package com.polete.roundlauncher

import android.app.Application
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.polete.roundlauncher.data.UApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val appCache = Container.appCache
    private val iconCache = Container.iconCache
    private val _apps = MutableStateFlow<List<UApp>>(emptyList())
    val apps: StateFlow<List<UApp>> = _apps.asStateFlow()

    init {
        reloadApps() // carga inicial
    }

    fun reloadApps() {
        viewModelScope.launch(Dispatchers.IO) {
            _apps.value = appCache.getApps()
            preloadIcons(_apps.value)
        }
    }

    fun clearAndReloadApps() {
        viewModelScope.launch {
            appCache.clearCache()
            _apps.value = appCache.getApps()
            clearIcons()
            preloadIcons(_apps.value)
        }
    }

    // IconCache
    private val _icons = mutableMapOf<String, MutableStateFlow<ImageBitmap?>>()
    val icons get() = _icons

    fun clearIcons() {
        _icons.clear()
    }

    suspend fun getIcon(uApp: UApp): ImageBitmap {
        return iconCache.getIcon(uApp)
    }

    suspend fun getApps(): List<UApp> {
        return appCache.getApps()
    }

    fun preloadIcons(uApps: List<UApp>) {

        uApps.forEach { app ->

            val key = "${app.packageName}-${app.user.hashCode()}"

            if (key !in _icons) {
                val flow = MutableStateFlow<ImageBitmap?>(null)
                _icons[key] = flow

                viewModelScope.launch(Dispatchers.IO) {
                    flow.value = iconCache.getIcon(app)
                }
            }

        }

    }


}
