package com.jig.UdemyReader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jig.UdemyReader.screens.ReaderSplashScreen
import com.jig.UdemyReader.screens.details.BookDetailsScreen
import com.jig.UdemyReader.screens.home.Home
import com.jig.UdemyReader.screens.login.Login
import com.jig.UdemyReader.screens.search.Search
import com.jig.UdemyReader.screens.stats.Stats
import com.jig.UdemyReader.screens.update.UpdateScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name){
        composable(ReaderScreens.SplashScreen.name){
            ReaderSplashScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderHomeScreen.name){
            Home(navController = navController)
        }
        composable(ReaderScreens.DetailScreen.name){
            BookDetailsScreen(navController = navController)
        }
        composable(ReaderScreens.LoginScreen.name){
            Login(navController = navController)
        }
        composable(ReaderScreens.SearchScreen.name){
            Search(navController = navController)
        }
        composable(ReaderScreens.ReaderStatsScreen.name){
            Stats(navController = navController)
        }
        composable(ReaderScreens.UpdateScreen.name){
            UpdateScreen(navController = navController)
        }

    }
}