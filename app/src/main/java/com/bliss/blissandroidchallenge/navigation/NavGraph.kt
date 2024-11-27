package com.bliss.blissandroidchallenge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bliss.blissandroidchallenge.presentation.emojiList.EmojiListScreen
import com.bliss.blissandroidchallenge.presentation.home.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val navigator = remember(navController) { AppNavigator(navController) }

    NavHost(
        navController = navController,
        startDestination = HOME_ROUTE,
        modifier = modifier
    ) {
        composable(HOME_ROUTE) {
            HomeScreen(navigator = navigator)
        }
        composable(EMOJI_LIST_ROUTE) {
            EmojiListScreen()
        }
    }
}