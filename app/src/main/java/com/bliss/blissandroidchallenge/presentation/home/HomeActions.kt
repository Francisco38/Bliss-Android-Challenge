package com.bliss.blissandroidchallenge.presentation.home

import com.bliss.blissandroidchallenge.domain.emojis.model.Emoji

sealed class HomeActions {
    data object RandomEmoji : HomeActions()
    data object GetEmojis : HomeActions()
}