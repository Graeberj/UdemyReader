package com.jig.UdemyReader.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jig.UdemyReader.screens.ReaderSplashScreen
import com.jig.UdemyReader.screens.details.BookDetailsScreen
import com.jig.UdemyReader.screens.home.Home
import com.jig.UdemyReader.screens.login.Login
import com.jig.UdemyReader.screens.search.Search
import com.jig.UdemyReader.screens.search.SearchViewModel
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
        val detailName = ReaderScreens.DetailScreen.name
        composable("$detailName/{bookId}", arguments = listOf(navArgument("bookId"){
            type = NavType.StringType
        })){ backStackEntry ->
            backStackEntry.arguments?.getString("bookId").let {
                BookDetailsScreen(navController = navController, bookId = it.toString())
            }
        }
        composable(ReaderScreens.LoginScreen.name){
            Login(navController = navController)
        }
        composable(ReaderScreens.SearchScreen.name){
            val viewModel = hiltViewModel<SearchViewModel>()
            Search(navController = navController, viewModel)
        }
        composable(ReaderScreens.ReaderStatsScreen.name){
            Stats(navController = navController)
        }
        composable(ReaderScreens.UpdateScreen.name){
            UpdateScreen(navController = navController)
        }

    }
}