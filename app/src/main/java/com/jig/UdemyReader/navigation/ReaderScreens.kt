package com.jig.UdemyReader.navigation

import com.jig.UdemyReader.screens.login.Login
import com.jig.UdemyReader.screens.search.Search

enum class ReaderScreens {
    SplashScreen,
    LoginScreen,
    CreateAccountScreen,
    ReaderHomeScreen,
    SearchScreen,
    DetailScreen,
    UpdateScreen,
    ReaderStatsScreen;
    companion object {
        fun fromRoute(route: String?): ReaderScreens =
            when(route?.substringBefore(delimiter = "/")){
                SplashScreen.name -> SplashScreen
                LoginScreen.name -> LoginScreen
                CreateAccountScreen.name -> CreateAccountScreen
                ReaderHomeScreen.name -> ReaderHomeScreen
                SearchScreen.name  -> SearchScreen
                DetailScreen.name -> DetailScreen
                UpdateScreen.name -> UpdateScreen
                ReaderStatsScreen.name  -> ReaderStatsScreen
                null -> ReaderHomeScreen
                else -> throw IllegalArgumentException("route $route doesn't exist")
            }
    }
}