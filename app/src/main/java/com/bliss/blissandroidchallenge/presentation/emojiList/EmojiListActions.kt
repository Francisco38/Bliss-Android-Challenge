package com.bliss.blissandroidchallenge.presentation.emojiList

import com.bliss.blissandroidchallenge.presentation.home.HomeActions

sealed class EmojiListActions {
    data object GetEmojis : EmojiListActions()
    data class DeleteEmoji(val emojiId: Int) : EmojiListActions()
}