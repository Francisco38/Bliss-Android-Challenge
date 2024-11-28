package com.bliss.blissandroidchallenge.domain.usersAvatar.useCases

import com.bliss.blissandroidchallenge.domain.usersAvatar.model.Avatar
import com.bliss.blissandroidchallenge.domain.usersAvatar.repository.AvatarRepository
import com.bliss.blissandroidchallenge.utils.UseCaseResponse
import javax.inject.Inject

class GetAvatarsUseCase @Inject constructor(
    private val avatarRepository: AvatarRepository
) {
    suspend operator fun invoke(): UseCaseResponse<List<Avatar>> =
        try {
            val avatarList = avatarRepository.getAllAvatars()

            UseCaseResponse.Success(avatarList)
        } catch (ex: Exception) {
            UseCaseResponse.Error("Error getting data")
        }
}