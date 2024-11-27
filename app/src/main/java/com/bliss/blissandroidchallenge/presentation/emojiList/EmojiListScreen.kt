package com.bliss.blissandroidchallenge.presentation.emojiList

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bliss.blissandroidchallenge.navigation.AppNavigator
import com.bliss.blissandroidchallenge.presentation.components.ImageComponent
import com.bliss.blissandroidchallenge.presentation.home.EmojiActions
import com.bliss.blissandroidchallenge.presentation.home.EmojiIcon
import com.bliss.blissandroidchallenge.presentation.home.HomeState
import com.bliss.blissandroidchallenge.presentation.home.HomeViewModel
import com.bliss.blissandroidchallenge.presentation.ui.theme.White

@Composable
fun EmojiListScreen(
    modifier: Modifier = Modifier,
    viewModel: EmojiListViewModel = hiltViewModel(),
    navigator: AppNavigator
) {
    val state by remember(viewModel) { viewModel.state }.collectAsState()

    EmojiListScreenContent(
        modifier = modifier,
        data = state,
        onEmojiClick = {it -> viewModel.deleteEmoji(it) },
        onPullToRefresh = {viewModel.refresh()}
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EmojiListScreenContent(
    modifier: Modifier = Modifier,
    data: EmojiListState,
    onEmojiClick: (Int) -> Unit,
    onPullToRefresh: () -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = data.isRefreshing,
        onRefresh = onPullToRefresh
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 75.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            itemsIndexed(data.emojiList) { _, item ->
                ImageComponent(
                    imageURL = item.url,
                    contentDescription = "test",
                    modifier = modifier
                        .size(100.dp)
                        .border(
                            width = 2.dp,
                            color = White,
                            shape = RoundedCornerShape(15)
                        )
                        .clickable {
                            onEmojiClick(item.id)
                        }
                )
            }
        }

        PullRefreshIndicator(
            refreshing = data.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}