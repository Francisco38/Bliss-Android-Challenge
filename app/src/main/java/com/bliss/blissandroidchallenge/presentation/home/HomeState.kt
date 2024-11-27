package com.bliss.blissandroidchallenge.presentation.home

import com.bliss.blissandroidchallenge.domain.emojis.model.Emoji
import com.google.common.collect.ImmutableList

data class HomeState (
    val isLoading: Boolean = true,
    val emojiList: ImmutableList<Emoji> = ImmutableList.of(),
    val error: String? = null,
    val emojiPosition: Int = 0
)