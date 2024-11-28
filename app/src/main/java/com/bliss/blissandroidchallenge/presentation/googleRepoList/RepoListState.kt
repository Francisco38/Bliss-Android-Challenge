package com.bliss.blissandroidchallenge.presentation.googleRepoList

import com.bliss.blissandroidchallenge.domain.usersRepo.model.Repo
import com.google.common.collect.ImmutableList

data class RepoListState(
    val isLoading: Boolean = true,
    val repoList: ImmutableList<Repo> = ImmutableList.of(),
    val error: String? = null,
    val endReached: Boolean =false,
    val pageNumber: Int = 1
)