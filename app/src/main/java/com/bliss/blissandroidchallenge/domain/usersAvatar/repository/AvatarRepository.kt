package com.bliss.blissandroidchallenge.domain.usersAvatar.repository

import com.bliss.blissandroidchallenge.domain.usersAvatar.model.Avatar

interface AvatarRepository {
    suspend fun getAvatar(username: String): Avatar

    suspend fun getAllAvatar(username: String): List<Avatar>

    suspend fun deleteAvatar(id: Int): List<Avatar>
}