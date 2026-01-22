package com.polete.roundlauncher

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.polete.roundlauncher.data.UApp
import com.polete.roundlauncher.system.getAppList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _appList = MutableStateFlow<List<UApp>>(emptyList())
    val appList = _appList

    init {
        loadApps()
    }

    fun loadApps() {
        viewModelScope.launch {
            _appList.value = getAppList(application.applicationContext)
        }
    }

}