package com.bliss.blissandroidchallenge.data.usersAvatar

import com.bliss.blissandroidchallenge.data.usersAvatar.local.dao.AvatarDao
import com.bliss.blissandroidchallenge.data.usersAvatar.remote.avatarGetRequest
import com.bliss.blissandroidchallenge.domain.usersAvatar.model.Avatar
import com.bliss.blissandroidchallenge.domain.usersAvatar.repository.AvatarRepository
import javax.inject.Inject

class AvatarRepositoryImpl @Inject constructor(
    private val avatarDao: AvatarDao
) : AvatarRepository {

    override suspend fun getAvatar(username: String): Avatar {
        var avatar = avatarDao.getAvatar(username.trim())

        if (avatar == null) {
            avatar = avatarGetRequest(username)
            avatarDao.insertAll(avatar)
        }

        return avatar
    }

    override suspend fun getAllAvatars(): List<Avatar> {
        return avatarDao.getAll()
    }

    override suspend fun deleteAvatar(id: Int): List<Avatar> {
        avatarDao.deleteAvatar(id)

        return avatarDao.getAll()
    }
}