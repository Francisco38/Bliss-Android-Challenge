package com.bliss.blissandroidchallenge.domain.usersAvatar.useCases

import com.bliss.blissandroidchallenge.domain.usersAvatar.model.Avatar
import com.bliss.blissandroidchallenge.domain.usersAvatar.repository.AvatarRepository
import com.bliss.blissandroidchallenge.utils.UseCaseResponse
import javax.inject.Inject

class GetAvatarUseCase @Inject constructor(
    private val avatarRepository: AvatarRepository
) {
    suspend operator fun invoke(username: String): UseCaseResponse<Avatar> =
        try {
            val emojiList = avatarRepository.getAvatar(username)

            UseCaseResponse.Success(emojiList)
        } catch (ex: Exception) {
            UseCaseResponse.Error("User does not exist")
        }
}