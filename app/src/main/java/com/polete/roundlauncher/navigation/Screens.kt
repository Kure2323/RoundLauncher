package com.polete.roundlauncher.navigation

sealed class Screens(val route: String) {

    object DrawPage: Screens("draw_page")
    object HomePage: Screens("home_page")
    object Settings: Screens("settings_page")



}