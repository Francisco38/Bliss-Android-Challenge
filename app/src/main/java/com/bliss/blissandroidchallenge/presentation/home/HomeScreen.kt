package com.bliss.blissandroidchallenge.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bliss.blissandroidchallenge.R
import com.bliss.blissandroidchallenge.presentation.components.TextButton
import com.bliss.blissandroidchallenge.presentation.ui.theme.BlissAndroidChallengeTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by remember(viewModel) { viewModel.state }.collectAsState()

    HomeScreenContent(
        modifier = modifier,
        randomEmoji = { viewModel.randomEmoji() },
        data = state
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    randomEmoji: () -> Unit,
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
                onRandomButtonClick = randomEmoji
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
    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        modifier = modifier
            .size(150.dp)
            .padding(15.dp)
    )
}

@Composable
fun EmojiActions(
    modifier: Modifier = Modifier,
    onRandomButtonClick: () -> Unit
) {

    Column(
        modifier = modifier,
    ) {
        TextButton(
            onButtonClick = onRandomButtonClick,
            textRes = R.string.random_emoji
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    BlissAndroidChallengeTheme {
        HomeScreen()
    }
}