package com.bliss.blissandroidchallenge.domain.usersAvatar.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val AVATAR_TABLE = "avatar_table"

@Entity(tableName = AVATAR_TABLE)
data class Avatar(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "avatar_url") @SerializedName("avatar_url") val avatarUrl: String
)