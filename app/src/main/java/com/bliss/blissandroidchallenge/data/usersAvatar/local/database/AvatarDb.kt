package com.bliss.blissandroidchallenge.data.usersAvatar.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bliss.blissandroidchallenge.data.usersAvatar.local.dao.AvatarDao
import com.bliss.blissandroidchallenge.domain.usersAvatar.model.Avatar

@Database(
    entities = [Avatar::class],
    version = 5,
    exportSchema = false
)
abstract class AvatarDb : RoomDatabase() {
    abstract fun avatarDao(): AvatarDao
}