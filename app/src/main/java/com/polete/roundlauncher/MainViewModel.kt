package com.polete.roundlauncher

import android.app.Application
import android.content.Context
import android.content.pm.LauncherApps
import android.graphics.Bitmap
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.polete.roundlauncher.data.UApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val application: Application) : AndroidViewModel(application) {

    private val appCache = Container.appCache
    private val iconCache = Container.iconCache

    private val _appList = MutableStateFlow<List<UApp>>(emptyList())
    val appList: StateFlow<List<UApp>> = _appList

    private val _iconList = MutableStateFlow<Map<String, Bitmap?>>(emptyMap())
    val iconList = _iconList

    init {
        loadApps()
        onAppsChanged()
    }

    /**
     * Es una coroutine stuck, e importante, reactiva.
     * Cada vez que el BroadCastReceiver le haga un 'emit()'
     * volverá a saltar este trozo de código y por tanto a
     * volver a cargarse las applicaciones correctamente
     */
    private fun onAppsChanged() {
        viewModelScope.launch {
            Container.appsChangedFlow.collect {
                loadApps()
            }
        }
    }

    /**
     * Recoge y guarda todas las applications en el flow, además
     * carga y guarda con su 'key' propia en un map los iconos
     * de cada una de ellas.
     */
    private fun loadApps() {
        viewModelScope.launch(Dispatchers.Default) {

            val apps = appCache.getApps()
            launch(Dispatchers.Main) {
                _appList.value = apps
            }

            val icons = buildMap {
                apps.forEach { app ->
                    val key = "${app.packageName}-${app.user.hashCode()}"
                    put(key, getIcon(app))
                }

            }

            launch(Dispatchers.Main) {
                _iconList.value = icons
            }


        }
    }


    private suspend fun getIcon(uApp: UApp): Bitmap {
        return iconCache.getIcon(uApp)
    }

    fun launchUApp(app: UApp) {
        try {
            val launcherApps = application.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps
            launcherApps.startMainActivity(app.componentName, app.user, null, null)
        } catch (_: Exception) {
            Toast.makeText(application, R.string.app_launch_error,Toast.LENGTH_SHORT).show()
            loadApps()
        }
    }
}
