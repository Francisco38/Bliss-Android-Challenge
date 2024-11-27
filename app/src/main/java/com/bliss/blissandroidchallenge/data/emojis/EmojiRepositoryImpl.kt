package com.bliss.blissandroidchallenge.data.emojis

import com.bliss.blissandroidchallenge.data.emojis.local.dao.EmojiDao
import com.bliss.blissandroidchallenge.data.emojis.remote.emojiGetRequest
import com.bliss.blissandroidchallenge.domain.emojis.model.Emoji
import com.bliss.blissandroidchallenge.domain.emojis.repository.EmojiRepository
import javax.inject.Inject

class EmojiRepositoryImpl @Inject constructor(
    private val emojiDao: EmojiDao
) : EmojiRepository {
    override suspend fun getEmojiList(): List<Emoji> {
        var emojiList = emojiDao.getAll()

        if (emojiList.isEmpty()) {
            emojiList = emojiGetRequest()

            emojiDao.insertAll(*emojiList.toTypedArray())

            return emojiList
        }
        else{
            return emojiList
        }
    }
}