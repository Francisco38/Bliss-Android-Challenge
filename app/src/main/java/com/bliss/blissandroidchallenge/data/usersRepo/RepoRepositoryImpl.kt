package com.bliss.blissandroidchallenge.data.usersRepo

import com.bliss.blissandroidchallenge.data.usersRepo.remote.repoGetRequest
import com.bliss.blissandroidchallenge.domain.usersRepo.model.Repo
import com.bliss.blissandroidchallenge.domain.usersRepo.repository.RepoRepository
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor() : RepoRepository {

    override suspend fun getGoogleRepos(page: Int): List<Repo> {
        return repoGetRequest(page)
    }
}