package com.bliss.blissandroidchallenge.presentation.googleRepoList

sealed class RepoListActions {
    data object GetRepos: RepoListActions()
    data object ClearError : RepoListActions()
}