package com.bliss.blissandroidchallenge.data.emojis.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bliss.blissandroidchallenge.data.emojis.local.dao.EmojiDao
import com.bliss.blissandroidchallenge.domain.emojis.model.Emoji

@Database(
    entities = [Emoji::class],
    version = 1,
    exportSchema = false
)
abstract class EmojiDb : RoomDatabase() {
    abstract fun emojiDao(): EmojiDao
}