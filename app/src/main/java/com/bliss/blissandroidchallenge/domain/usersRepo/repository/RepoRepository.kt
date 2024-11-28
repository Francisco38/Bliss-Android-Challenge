package com.bliss.blissandroidchallenge.domain.usersRepo.repository

import com.bliss.blissandroidchallenge.domain.usersRepo.model.Repo

interface RepoRepository {
    suspend fun getGoogleRepos(page: Int) : List<Repo>
}