package com.bliss.blissandroidchallenge.data.usersAvatar.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bliss.blissandroidchallenge.domain.usersAvatar.model.AVATAR_TABLE
import com.bliss.blissandroidchallenge.domain.usersAvatar.model.Avatar

@Dao
interface  AvatarDao {
    @Query("SELECT * FROM $AVATAR_TABLE")
    fun getAll(): List<Avatar>

    @Query("SELECT * FROM $AVATAR_TABLE WHERE id = :id")
    fun getAvatar(id: Int): Avatar

    @Query("SELECT * FROM $AVATAR_TABLE WHERE login = :username")
    fun getAvatar(username: String): Avatar?

    @Query("DELETE FROM $AVATAR_TABLE WHERE id = :id")
    fun deleteAvatar(id: Int)

    @Insert
    fun insertAll(vararg avatar: Avatar)
}