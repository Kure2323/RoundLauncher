package com.polete.roundlauncher

import android.app.Application
import android.content.Context
import android.content.pm.LauncherApps
import android.graphics.Bitmap
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.polete.roundlauncher.data.UApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val application: Application) : AndroidViewModel(application) {

    private val appCache = Container.appCache
    private val iconCache = Container.iconCache

    private val _appList = MutableStateFlow<List<UApp>>(emptyList())
    val appList: StateFlow<List<UApp>> = _appList

    private val _iconList = MutableStateFlow<Map<String, Bitmap>>(emptyMap())
    val iconList = _iconList

    init {
        loadApps()
    }

    private fun loadApps() {
        viewModelScope.launch {
            _appList.value = appCache.getApps()
            appList.value.forEach { app ->
                val key = "${app.packageName}-${app.user.hashCode()}"
                _iconList.update {
                    it + (key to getIcon(app))
                }
            }
        }
    }

    suspend fun getIcon(uApp: UApp): Bitmap {
        return iconCache.getIcon(uApp)
    }

    fun launchUApp(app: UApp) {
        try {
            val launcherApps = application.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps
            launcherApps.startMainActivity(app.componentName, app.user, null, null)
        } catch (_: Exception) {
            Toast.makeText(application, R.string.app_launch_error,Toast.LENGTH_SHORT).show()
        }
    }
}
