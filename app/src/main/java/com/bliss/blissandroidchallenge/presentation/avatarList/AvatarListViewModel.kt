package com.bliss.blissandroidchallenge.presentation.avatarList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bliss.blissandroidchallenge.di.IoDispatcher
import com.bliss.blissandroidchallenge.domain.usersAvatar.useCases.DeleteAvatarUseCase
import com.bliss.blissandroidchallenge.domain.usersAvatar.useCases.GetAvatarsUseCase
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
class AvatarListViewModel @Inject constructor(
    private val getAvatarList: GetAvatarsUseCase,
    private val deleteAvatar: DeleteAvatarUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = MutableStateFlow(value = AvatarListState())

    val state: StateFlow<AvatarListState> by lazy {
        _state.asStateFlow()
            .also { getData() }
    }

    private val _actions: MutableSharedFlow<AvatarListActions> by lazy {
        MutableSharedFlow<AvatarListActions>(replay = 1)
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
        newState: AvatarListState
    ) {
        _state.update { newState }
    }

    private fun emitAction(action: AvatarListActions) {
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    fun getData() {
        emitAction(AvatarListActions.GetAvatars)
    }

    fun deleteEmoji(avatarId: Int) {
        emitAction(AvatarListActions.DeleteAvatar(avatarId))
    }

    fun clearError() {
        emitAction(AvatarListActions.ClearError)
    }

    private fun processAction(
        action: AvatarListActions
    ) = when (action) {
        is AvatarListActions.GetAvatars -> processGetData()
        is AvatarListActions.DeleteAvatar -> processDeleteEmoji(action.avatarId)
        is AvatarListActions.ClearError -> processClearError()
    }

    private fun processGetData() = flow {
        emit(
            AvatarListState(
                error = null,
                isLoading = true,
            )
        )

        val avatarList = getAvatarList()

        if (avatarList is UseCaseResponse.Success) {
            emit(
                AvatarListState(
                    error = null,
                    isLoading = false,
                    avatarList = ImmutableList.copyOf(avatarList.data)
                )
            )
        } else if (avatarList is UseCaseResponse.Error) {
            emit(
                AvatarListState(
                    error = avatarList.error,
                    isLoading = false
                )
            )
        }
    }

    private fun processDeleteEmoji(avatarId: Int) = flow {
        emit(
            AvatarListState(
                error = null,
                isLoading = true,
            )
        )

        val newAvatarList = deleteAvatar(avatarId)

        if (newAvatarList is UseCaseResponse.Success) {
            emit(
                AvatarListState(
                    error = null,
                    isLoading = false,
                    avatarList = ImmutableList.copyOf(newAvatarList.data)
                )
            )
        } else if (newAvatarList is UseCaseResponse.Error) {
            emit(
                AvatarListState(
                    error = newAvatarList.error,
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