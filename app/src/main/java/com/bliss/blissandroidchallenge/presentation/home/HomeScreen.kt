package com.bliss.blissandroidchallenge.presentation.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        onAvatarListButtonClick = { navigator.goToAvatarList() },
        onGoogleReposButtonClick = { navigator.goToGooglesRepos() },
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
    onAvatarListButtonClick: () -> Unit,
    onGoogleReposButtonClick: () -> Unit,
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
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
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

            AvatarListButton(
                onAvatarListButtonClick = onAvatarListButtonClick
            )

            GoogleReposButton(
                onAvatarListButtonClick = onGoogleReposButtonClick
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
    Column(
        modifier = modifier,
    ) {
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onButtonClick = onRandomButtonClick,
            textRes = R.string.random_emoji
        )

        TextButton(
            modifier = Modifier.fillMaxWidth(),
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
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 4.dp)
            .height(55.dp)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            value = searchText,
            onValueChange = { onSearchTextChange(it) },
            placeholder = {
                Text(
                    stringResource(
                        id = R.string.github_username_placeholder
                    )
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(topStartPercent = 25, bottomStartPercent = 25),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        IconButton(
            modifier = Modifier.fillMaxHeight(),
            onButtonClick = { onSearchClick(searchText) },
            iconRes = R.drawable.ic_search,
            shape = RoundedCornerShape(
                topStartPercent = 0,
                bottomStartPercent = 0,
                topEndPercent = 25,
                bottomEndPercent = 25
            ),
            contentDescRes = R.string.github_username_placeholder
        )
    }
}

@Composable
fun AvatarListButton(
    modifier: Modifier = Modifier,
    onAvatarListButtonClick: () -> Unit
) {
    TextButton(
        modifier = modifier.fillMaxWidth(),
        onButtonClick = onAvatarListButtonClick,
        textRes = R.string.avatar_list
    )
}

@Composable
fun GoogleReposButton(
    modifier: Modifier = Modifier,
    onAvatarListButtonClick: () -> Unit
) {
    TextButton(
        modifier = modifier.fillMaxWidth(),
        onButtonClick = onAvatarListButtonClick,
        textRes = R.string.google_repos
    )
}