package com.bliss.blissandroidchallenge.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bliss.blissandroidchallenge.di.IoDispatcher
import com.bliss.blissandroidchallenge.domain.emojis.useCases.GetEmojiListUseCase
import com.bliss.blissandroidchallenge.domain.usersAvatar.useCases.GetAvatarUseCase
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
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getEmojiList: GetEmojiListUseCase,
    private val getAvatarUseCase: GetAvatarUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = MutableStateFlow(value = HomeState())

    val state: StateFlow<HomeState> by lazy {
        _state.asStateFlow()
            .also { getData() }
    }

    private val _actions: MutableSharedFlow<HomeActions> by lazy {
        MutableSharedFlow<HomeActions>(replay = 1)
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
        newState: HomeState
    ) {
        _state.update { newState }
    }

    private fun emitAction(action: HomeActions) {
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    fun getData() {
        emitAction(HomeActions.GetEmojis)
    }

    fun randomEmoji() {
        emitAction(HomeActions.RandomEmoji)
    }

    fun searchGitAvatar(username: String) {
        emitAction(HomeActions.SearchGitAvatar(username))
    }

    fun searchTextChange(searchText: String) {
        emitAction(HomeActions.SearchTextChange(searchText))
    }

    fun clearError() {
        emitAction(HomeActions.ClearError)
    }

    private fun processAction(
        action: HomeActions
    ) = when (action) {
        is HomeActions.GetEmojis -> processGetData()
        is HomeActions.RandomEmoji -> processRandomEmoji()
        is HomeActions.SearchGitAvatar -> processSearchGitAvatar(action.username)
        is HomeActions.SearchTextChange -> processSearchTextChange(action.searchText)
        is HomeActions.ClearError -> processClearError()
    }

    private fun processGetData() = flow {
        val currentData = _state.value

        emit(
            currentData.copy(isLoading = true)
        )

        val emojiList = getEmojiList()

        if (emojiList is UseCaseResponse.Success) {
            emit(
                HomeState(
                    error = null,
                    isLoading = false,
                    emojiList = ImmutableList.copyOf(emojiList.data),
                    imageUrl = emojiList.data[Random.nextInt(0, emojiList.data.size)].url
                )
            )
        } else if (emojiList is UseCaseResponse.Error) {
            emit(
                currentData.copy(error = emojiList.error)
            )
        }
    }

    private fun processSearchGitAvatar(username: String) = flow {
        val currentData = _state.value

        val avatar = getAvatarUseCase(username)

        emit(
            currentData.copy(
                error = null,
                isLoading = true
            )
        )

        if (avatar is UseCaseResponse.Success) {
            emit(
                HomeState(
                    error = null,
                    isLoading = false,
                    emojiList = currentData.emojiList,
                    imageUrl = avatar.data.avatarUrl,
                    searchText = ""
                )
            )
        } else if (avatar is UseCaseResponse.Error) {
            emit(
                HomeState(
                    error = avatar.error,
                    isLoading = false,
                    emojiList = currentData.emojiList,
                    imageUrl = currentData.imageUrl,
                    searchText = ""
                )
            )
        }
    }

    private fun processRandomEmoji() = flow {
        val currentData = _state.value

        emit(
            currentData.copy(
                imageUrl = currentData.emojiList[Random.nextInt(
                    0,
                    currentData.emojiList.size
                )].url
            )
        )
    }

    private fun processSearchTextChange(searchText: String) = flow {
        val currentData = _state.value

        emit(
            currentData.copy(searchText = searchText)
        )
    }

    private fun processClearError() = flow {
        val currentData = _state.value

        emit(
            currentData.copy(error = null)
        )
    }
}