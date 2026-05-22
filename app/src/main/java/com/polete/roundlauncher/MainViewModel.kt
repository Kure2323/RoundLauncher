package com.polete.roundlauncher

import android.app.Application
import android.content.Context
import android.content.pm.LauncherApps
import android.graphics.Bitmap
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.polete.roundlauncher.data.UApp
import com.polete.roundlauncher.data.local.entity.AppKey
import com.polete.roundlauncher.system.getKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val application: Application) : AndroidViewModel(application) {

    private val repo = Container.repo

    val dbList = repo.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    private val appCache = Container.appCache
    private val iconCache = Container.iconCache

    private val _appList = MutableStateFlow<List<UApp>>(emptyList())
    val appList: StateFlow<List<UApp>> = _appList

    private val _iconList = MutableStateFlow<Map<String, Bitmap>>(emptyMap())
    val iconList: StateFlow<Map<String, Bitmap>> = _iconList

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
        viewModelScope.launch(Dispatchers.IO) {

            val apps = appCache.getApps(application)

            /*
            Recolecta todos los iconos paralelamente y luego los hace map una vez
            todos estén cargados y almacenados, de esta forma no se van cargando de
            uno en uno y es mucho más rápido.
             */
            val icons = coroutineScope {

                apps.map { app ->

                    async {

                        getKey(app) to getIcon(app)

                    }
                }.awaitAll().toMap()
            }

            withContext(Dispatchers.Main) {

                _appList.value = apps
                _iconList.value = icons
            }
        }
    }

    /**
     * CUIDADO CON DONDE USAS ESTO POL, NO TE PASES QUE EXPLOTA TODO
     * Y NO QUEREMOS ESO
     */
    private suspend fun getIcon(uApp: UApp): Bitmap {
        return iconCache.getIcon(uApp, application)
    }

    fun insertRL(app: UApp) {
        viewModelScope.launch {
            repo.insert(
                AppKey(
                    key = getKey(app)
                )
            )
        }
    }

    fun deleteRL(app: UApp) {
        viewModelScope.launch {
            repo.delete(
                AppKey(
                    key = getKey(app)
                )
            )
        }
    }

    fun appCheckBoxAction(app: UApp): Boolean {
        val key = getKey(app)

        if (dbList.value.contains(key)) {
            deleteRL(app)
            return false
        } else {
            insertRL(app)
            return true
        }

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
