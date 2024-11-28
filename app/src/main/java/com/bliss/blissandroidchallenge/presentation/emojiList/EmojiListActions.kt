package com.bliss.blissandroidchallenge.presentation.emojiList

sealed class EmojiListActions {
    data object GetEmojis : EmojiListActions()
    data object Refresh : EmojiListActions()
    data object ClearError : EmojiListActions()
    data class DeleteEmoji(val emojiId: Int) : EmojiListActions()
}