package com.bliss.blissandroidchallenge.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bliss.blissandroidchallenge.di.IoDispatcher
import com.bliss.blissandroidchallenge.domain.emojis.useCases.GetEmojiListUseCase
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

    private fun processAction(
        action: HomeActions
    ) = when (action) {
        is HomeActions.GetEmojis -> processGetData()
        is HomeActions.RandomEmoji -> processRandomEmoji()
    }

    private fun processGetData() = flow {
        emit(
            HomeState(
                error = null,
                isLoading = true,
            )
        )

        val emojiList = getEmojiList()

        if (emojiList is UseCaseResponse.Success) {
            emit(
                HomeState(
                    error = null,
                    isLoading = false,
                    emojiList = ImmutableList.copyOf(emojiList.data),
                    emojiPosition = Random.nextInt(0, emojiList.data.size)
                )
            )
        } else if (emojiList is UseCaseResponse.Error) {
            emit(
                HomeState(
                    error = emojiList.error,
                    isLoading = false
                )
            )
        }
    }

    private fun processRandomEmoji() = flow {
        val currentData = _state.value

        emit(
            currentData.copy(emojiPosition = Random.nextInt(0, currentData.emojiList.size))
        )
    }
}