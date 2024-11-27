package com.bliss.blissandroidchallenge.navigation

import android.content.Context
import androidx.navigation.NavHostController
import javax.inject.Inject

class AppNavigator @Inject constructor(
    private val controller: NavHostController
) : Navigator {
    override fun goToEmojiList() {
        controller.navigate(route = EMOJI_LIST_ROUTE) {
            launchSingleTop = true
        }
    }
}