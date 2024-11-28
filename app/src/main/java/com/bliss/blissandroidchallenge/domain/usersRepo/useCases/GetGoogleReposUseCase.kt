package com.bliss.blissandroidchallenge.domain.usersRepo.useCases

import com.bliss.blissandroidchallenge.domain.usersRepo.model.Repo
import com.bliss.blissandroidchallenge.domain.usersRepo.repository.RepoRepository
import com.bliss.blissandroidchallenge.utils.UseCaseResponse
import javax.inject.Inject

class GetGoogleReposUseCase @Inject constructor(
    private val repoRepository: RepoRepository
) {
    suspend operator fun invoke(page:Int): UseCaseResponse<List<Repo>> =
        try {
            val repoList = repoRepository.getGoogleRepos(page)

            UseCaseResponse.Success(repoList)
        } catch (ex: Exception) {
            UseCaseResponse.Error("Error getting data")
        }
}