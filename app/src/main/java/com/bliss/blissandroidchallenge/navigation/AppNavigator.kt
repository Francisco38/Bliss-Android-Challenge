package com.bliss.blissandroidchallenge.navigation

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

    override fun goToAvatarList() {
        controller.navigate(route = AVATAR_LIST_ROUTE) {
            launchSingleTop = true
        }
    }

    override fun goToGooglesRepos() {
        controller.navigate(route = GOOGLE_REPOS_ROUTE) {
            launchSingleTop = true
        }
    }
}