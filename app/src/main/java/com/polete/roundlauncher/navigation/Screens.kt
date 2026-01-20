package com.polete.roundlauncher.navigation

sealed class Screens() {

    object HomePage: Screens() {
        val route = "home_page"
    }

    object Settings: Screens() {
        val route = "settings_page"
    }



}