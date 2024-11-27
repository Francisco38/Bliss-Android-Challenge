package com.bliss.blissandroidchallenge.data.emojis.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bliss.blissandroidchallenge.domain.emojis.model.EMOJI_TABLE
import com.bliss.blissandroidchallenge.domain.emojis.model.Emoji

@Dao
interface  EmojiDao {
    @Query("SELECT * FROM $EMOJI_TABLE")
    fun getAll(): List<Emoji>

    @Insert
    fun insertAll(vararg emojis: Emoji)
}