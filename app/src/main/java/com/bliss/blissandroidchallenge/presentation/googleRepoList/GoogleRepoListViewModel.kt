package com.bliss.blissandroidchallenge.presentation.googleRepoList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bliss.blissandroidchallenge.di.IoDispatcher
import com.bliss.blissandroidchallenge.domain.usersRepo.useCases.GetGoogleReposUseCase
import com.bliss.blissandroidchallenge.utils.UseCaseResponse
import com.google.common.collect.ImmutableList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoogleRepoListViewModel @Inject constructor(
    private val getGoogleRepos: GetGoogleReposUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = MutableStateFlow(value = RepoListState())

    val state: StateFlow<RepoListState> by lazy {
        _state.asStateFlow()
            .also { getData() }
    }

    private val _actions: MutableSharedFlow<RepoListActions> by lazy {
        MutableSharedFlow<RepoListActions>(replay = 1)
            .also { subscribeToActions() }
    }

    private fun subscribeToActions() {
        viewModelScope.launch(dispatcher) {
            _actions.collect { action ->
                processAction(action).collect { newState ->
                    setState(newState)
                }
            }
        }
    }

    private fun setState(
        newState: RepoListState
    ) {
        _state.update { newState }
    }

    private fun emitAction(action: RepoListActions) {
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    fun getData() {
        emitAction(RepoListActions.GetRepos)
    }

    fun clearError() {
        emitAction(RepoListActions.ClearError)
    }

    private fun processAction(
        action: RepoListActions
    ) = when (action) {
        is RepoListActions.GetRepos -> processGetData()
        is RepoListActions.ClearError -> processClearError()
    }

    private fun processGetData() = flow {
        val currentData = _state.value

        emit(
            currentData.copy(
                isLoading = true
            )
        )

        val repoList = getGoogleRepos(currentData.pageNumber)

        if (repoList is UseCaseResponse.Success) {
            if (repoList.data.isEmpty()) {
                emit(
                    RepoListState(
                        error = null,
                        isLoading = false,
                        repoList = ImmutableList.copyOf(currentData.repoList),
                        endReached = true,
                        pageNumber = currentData.pageNumber+1
                    )
                )
            } else {
                emit(
                    RepoListState(
                        error = null,
                        isLoading = false,
                        repoList = ImmutableList.copyOf(currentData.repoList + repoList.data),
                        endReached = false,
                        pageNumber = currentData.pageNumber+1
                    )
                )
            }
        } else if (repoList is UseCaseResponse.Error) {
            emit(
                currentData.copy(
                    error = repoList.error,
                    isLoading = false
                )
            )
        }
    }


    private fun processClearError() = flow {
        val currentData = _state.value

        emit(
            currentData.copy(error = null)
        )
    }
}