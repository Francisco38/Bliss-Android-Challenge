package com.bliss.blissandroidchallenge.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bliss.blissandroidchallenge.R
import com.bliss.blissandroidchallenge.navigation.AppNavigator
import com.bliss.blissandroidchallenge.presentation.components.ImageComponent
import com.bliss.blissandroidchallenge.presentation.components.TextButton
import com.bliss.blissandroidchallenge.utils.toDp

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigator: AppNavigator
) {
    val state by remember(viewModel) { viewModel.state }.collectAsState()

    HomeScreenContent(
        modifier = modifier,
        onRandomButtonClick = { viewModel.randomEmoji() },
        onEmojiListButtonClick = { navigator.goToEmojiList() },
        data = state
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    onRandomButtonClick: () -> Unit,
    onEmojiListButtonClick: () -> Unit,
    data: HomeState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!data.isLoading) {
            EmojiIcon(
                url = data.emojiList[data.emojiPosition].url,
                contentDescription = "Image from URL"
            )

            EmojiActions(
                onRandomButtonClick = onRandomButtonClick,
                onEmojiListButtonClick = onEmojiListButtonClick
            )
        }
    }
}

@Composable
fun EmojiIcon(
    modifier: Modifier = Modifier,
    url: String,
    contentDescription: String
) {
    ImageComponent(
        imageURL = url,
        contentDescription = contentDescription,
        modifier = modifier
            .size(150.dp)
            .padding(15.dp)
    )
}

@Composable
fun EmojiActions(
    modifier: Modifier = Modifier,
    onRandomButtonClick: () -> Unit,
    onEmojiListButtonClick: () -> Unit
) {
    var randomButtonWidth by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier,
    ) {
        TextButton(
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    randomButtonWidth = coordinates.size.width
                },
            onButtonClick = onRandomButtonClick,
            textRes = R.string.random_emoji
        )

        TextButton(
            modifier = Modifier.width(randomButtonWidth.toDp()),
            onButtonClick = onEmojiListButtonClick,
            textRes = R.string.emoji_list
        )
    }
}