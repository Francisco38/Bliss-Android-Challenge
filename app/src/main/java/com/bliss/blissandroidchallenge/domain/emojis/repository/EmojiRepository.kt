package com.bliss.blissandroidchallenge.domain.emojis.repository

import com.bliss.blissandroidchallenge.domain.emojis.model.Emoji

interface EmojiRepository {
    suspend fun getEmojiList() : List<Emoji>
}