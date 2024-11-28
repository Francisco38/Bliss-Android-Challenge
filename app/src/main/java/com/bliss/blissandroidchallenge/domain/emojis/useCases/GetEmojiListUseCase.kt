package com.bliss.blissandroidchallenge.domain.emojis.useCases

import com.bliss.blissandroidchallenge.domain.emojis.model.Emoji
import com.bliss.blissandroidchallenge.domain.emojis.repository.EmojiRepository
import com.bliss.blissandroidchallenge.utils.UseCaseResponse
import javax.inject.Inject

class GetEmojiListUseCase @Inject constructor(
    private val emojiRepository: EmojiRepository
) {
    suspend operator fun invoke(): UseCaseResponse<List<Emoji>> =
        try {
            val emojiList = emojiRepository.getEmojiList()
            UseCaseResponse.Success(emojiList)
        } catch (ex: Exception) {
            UseCaseResponse.Error("Error obtaining data")
        }
}