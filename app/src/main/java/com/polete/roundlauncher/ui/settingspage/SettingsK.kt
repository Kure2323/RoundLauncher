package com.polete.roundlauncher.ui.settingspage

import android.content.Context

data class SettingsK(
    var rlHeight: Int = 100,
    var rlWidth: Int = 100,
    var sbIsInstant: Boolean = false,
    var drIsRdm: Boolean = false
) {

    fun applySettings(context: Context): SettingsK {
        val settings = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

        settings.edit()
            .putInt("rlHeight", rlHeight)
            .putInt("rlWidth", rlWidth)
            .putBoolean("sbIsInstant", sbIsInstant)
            .putBoolean("drIsRdm", drIsRdm)
            .apply()
        return loadSettings(context)
    }

    fun loadSettings(context: Context): SettingsK {
        val settings = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

        this.rlHeight = settings.getInt("rlHeight", 100)
        this.rlWidth = settings.getInt("rlWidth", 100)
        this.sbIsInstant = settings.getBoolean("sIsInstant", false)
        this.drIsRdm = settings.getBoolean("drIsRdm", false)

        return this
    }

}