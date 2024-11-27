package com.bliss.blissandroidchallenge.domain.emojis.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val EMOJI_TABLE = "emoji_table"

@Entity(tableName = EMOJI_TABLE)
data class Emoji(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "url") val url: String
)