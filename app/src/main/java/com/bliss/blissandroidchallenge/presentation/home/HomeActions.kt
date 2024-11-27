package com.bliss.blissandroidchallenge.presentation.home

sealed class HomeActions {
    data object RandomEmoji : HomeActions()
    data object GetEmojis : HomeActions()
}