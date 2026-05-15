package com.polete.roundlauncher.ui.settingspage

import android.content.Context
import com.polete.roundlauncher.Container

data class SettingsK(
    var rlHeight: Int = 100,
    var rlWidth: Int = 100,
    var sbIsInstant: Boolean = false,
    var drIsSorted: Boolean = false,
    var xOffset: Int = 0,
    var yOffset: Int = 0,
) {

    fun applySettings(context: Context): SettingsK {
        val settings = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

        settings.edit()
            .putInt("rlHeight", rlHeight)
            .putInt("rlWidth", rlWidth)
            .putBoolean("sbIsInstant", sbIsInstant)
            .putBoolean("drIsSorted", drIsSorted)
            .putInt("xOffset", xOffset)
            .putInt("yOffset", yOffset)
            .apply()
        val newSettings = loadSettings(context)
        Container.settings = newSettings
        return newSettings
    }

    fun loadSettings(context: Context): SettingsK {
        val settings = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

        this.rlHeight = settings.getInt("rlHeight", 100)
        this.rlWidth = settings.getInt("rlWidth", 100)
        this.sbIsInstant = settings.getBoolean("sbIsInstant", false)
        this.drIsSorted = settings.getBoolean("drIsSorted", true)
        this.xOffset = settings.getInt("xOffset", 0)
        this.yOffset = settings.getInt("yOffset", 0)

        return this
    }

}