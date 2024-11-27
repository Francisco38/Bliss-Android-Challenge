package com.bliss.blissandroidchallenge.presentation.emojiList

sealed class EmojiListActions {
    data object RandomEmoji : EmojiListActions()
    data object GetEmojis : EmojiListActions()
}