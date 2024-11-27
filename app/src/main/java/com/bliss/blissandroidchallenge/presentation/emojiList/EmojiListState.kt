package com.bliss.blissandroidchallenge.presentation.emojiList

import com.bliss.blissandroidchallenge.domain.emojis.model.Emoji
import com.google.common.collect.ImmutableList

data class EmojiListState (
    val isLoading: Boolean = true,
    val emojiList: ImmutableList<Emoji> = ImmutableList.of(),
    val error: String? = null
)