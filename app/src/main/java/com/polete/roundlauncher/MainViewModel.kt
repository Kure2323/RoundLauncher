package com.polete.roundlauncher

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.polete.roundlauncher.data.UApp
import com.polete.roundlauncher.system.cache.AppCache
import com.polete.roundlauncher.system.cache.IconCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val iconCache: IconCache,
    private val appCache: AppCache
) : ViewModel() {

    private val _apps = MutableStateFlow<List<UApp>>(emptyList())
    val apps: StateFlow<List<UApp>> = _apps.asStateFlow()

    init {
        reloadApps() // carga inicial
    }

    fun reloadApps() {
        viewModelScope.launch {
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
    val icons = _icons

    fun clearIcons() {
        _icons.clear()
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

    fun getIcon(uApp: UApp): StateFlow<ImageBitmap?> {
        val key = "${uApp.packageName}-${uApp.user.hashCode()}"
        return _icons.getOrPut(key) {
            MutableStateFlow<ImageBitmap?>(null).also { flow ->
                viewModelScope.launch {
                    val bitmap = iconCache.getIcon(uApp)
                    flow.value = bitmap
                }
            }
        }
    }


}

class MainViewModelFactory(
    private val iconCache: IconCache,
    private val appCache: AppCache
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                iconCache,
                appCache
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
