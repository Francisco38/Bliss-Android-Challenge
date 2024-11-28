package com.bliss.blissandroidchallenge.presentation.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bliss.blissandroidchallenge.R
import com.bliss.blissandroidchallenge.navigation.AppNavigator
import com.bliss.blissandroidchallenge.presentation.components.IconButton
import com.bliss.blissandroidchallenge.presentation.components.ImageComponent
import com.bliss.blissandroidchallenge.presentation.components.LoaderComponent
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
        onSearchGitAvatar = { username: String -> viewModel.searchGitAvatar(username) },
        onSearchTextChange = { searchText: String -> viewModel.searchTextChange(searchText) },
        onClearError = {viewModel.clearError()},
        data = state
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    onRandomButtonClick: () -> Unit,
    onEmojiListButtonClick: () -> Unit,
    onSearchGitAvatar: (String) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onClearError: () -> Unit,
    data: HomeState,
) {

    val context = LocalContext.current
    LaunchedEffect(data.error) {
        if (!data.error.isNullOrEmpty()) {
            Toast.makeText(context, data.error, Toast.LENGTH_SHORT).show()
            onClearError()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!data.isLoading) {
            data.imageUrl?.let {
                EmojiIcon(
                    url = it,
                    contentDescription = "Image from URL"
                )
            }

            EmojiActions(
                onRandomButtonClick = onRandomButtonClick,
                onEmojiListButtonClick = onEmojiListButtonClick
            )

            GitHubAvatarSearch(
                onSearchClick = onSearchGitAvatar,
                onSearchTextChange = onSearchTextChange,
                searchText = data.searchText,
            )
        } else {
            LoaderComponent()
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

@Composable
fun GitHubAvatarSearch(
    modifier: Modifier = Modifier,
    onSearchClick: (String) -> Unit,
    onSearchTextChange: (String) -> Unit,
    searchText: String
) {

    Row(
        modifier = modifier.padding(top = 32.dp)
    ) {
        TextField(
            value = searchText,
            onValueChange = { onSearchTextChange(it) },
            placeholder = {
                Text(
                    stringResource(
                        id = R.string.github_username_placeholder
                    )
                )
            },
            singleLine = true
        )
        IconButton(
            onButtonClick = { onSearchClick(searchText) },
            iconRes = R.drawable.ic_search,
            contentDescRes = R.string.github_username_placeholder
        )
    }
}