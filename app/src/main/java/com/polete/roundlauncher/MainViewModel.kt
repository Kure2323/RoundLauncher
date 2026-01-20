package com.polete.roundlauncher

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.polete.roundlauncher.data.AppModule
import com.polete.roundlauncher.system.getAppList
import com.polete.roundlauncher.system.getWallpaperBitMap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _appList = MutableStateFlow<List<AppModule>>(emptyList())
    val appList = _appList

    private val _wallpaper = MutableStateFlow<Bitmap?>(null)
    val wallpaper = _wallpaper

    init {
        loadApps()
        loadWallpaper()
    }

    fun loadApps() {
        viewModelScope.launch {
            _appList.value = getAppList(application.applicationContext)
        }
    }

    fun loadWallpaper() {
        viewModelScope.launch {
            _wallpaper.value = getWallpaperBitMap(application.applicationContext)
        }
    }

}