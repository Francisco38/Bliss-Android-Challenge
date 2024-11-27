package com.bliss.blissandroidchallenge.presentation.emojiList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bliss.blissandroidchallenge.di.IoDispatcher
import com.bliss.blissandroidchallenge.domain.emojis.useCases.GetEmojiListUseCase
import com.bliss.blissandroidchallenge.presentation.home.HomeActions
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
class EmojiListViewModel @Inject constructor(
    private val getEmojiList: GetEmojiListUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = MutableStateFlow(value = EmojiListState())

    val state: StateFlow<EmojiListState> by lazy {
        _state.asStateFlow()
            .also { getData() }
    }

    private val _actions: MutableSharedFlow<EmojiListActions> by lazy {
        MutableSharedFlow<EmojiListActions>(replay = 1)
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
        newState: EmojiListState
    ) {
        _state.update { newState }
    }

    private fun emitAction(action: EmojiListActions) {
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    fun getData() {
        emitAction(EmojiListActions.GetEmojis)
    }

    fun deleteEmoji( emojiId: Int ) {
        emitAction(EmojiListActions.DeleteEmoji(emojiId))
    }

    fun refresh() {
        emitAction(EmojiListActions.Refresh)
    }

    private fun processAction(
        action: EmojiListActions
    ) = when (action) {
        is EmojiListActions.GetEmojis -> processGetData()
        is EmojiListActions.Refresh-> processRefresh()
        is EmojiListActions.DeleteEmoji -> processDeleteEmoji(action.emojiId)
    }

    private fun processGetData() = flow {
        emit(
            EmojiListState(
                error = null,
                isLoading = true,
            )
        )

        val emojiList = getEmojiList()

        if (emojiList is UseCaseResponse.Success) {
            emit(
                EmojiListState(
                    error = null,
                    isLoading = false,
                    emojiList = ImmutableList.copyOf(emojiList.data)
                )
            )
        } else if (emojiList is UseCaseResponse.Error) {
            emit(
                EmojiListState(
                    error = emojiList.error,
                    isLoading = false
                )
            )
        }
    }

    private fun processDeleteEmoji(emojiId: Int) = flow {
        val currentState = _state.value
        val updatedList = currentState.emojiList.filter { it.id != emojiId } // Remove the emoji

        emit(
            currentState.copy(
                emojiList = ImmutableList.copyOf(updatedList)
            )
        )
    }

    private fun processRefresh() = flow {
        emit(
            EmojiListState(
                error = null,
                isLoading = false,
                isRefreshing = true
            )
        )

        val emojiList = getEmojiList()

        if (emojiList is UseCaseResponse.Success) {
            emit(
                EmojiListState(
                    error = null,
                    isLoading = false,
                    emojiList = ImmutableList.copyOf(emojiList.data),
                    isRefreshing = false
                )
            )
        } else if (emojiList is UseCaseResponse.Error) {
            emit(
                EmojiListState(
                    error = emojiList.error,
                    isLoading = false,
                    isRefreshing = false
                )
            )
        }
    }
}