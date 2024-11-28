package com.bliss.blissandroidchallenge.presentation.home

sealed class HomeActions {
    data object RandomEmoji : HomeActions()
    data object GetEmojis : HomeActions()
    data object ClearError : HomeActions()
    data class SearchGitAvatar(val username: String) : HomeActions()
    data class SearchTextChange(val searchText: String) : HomeActions()
}