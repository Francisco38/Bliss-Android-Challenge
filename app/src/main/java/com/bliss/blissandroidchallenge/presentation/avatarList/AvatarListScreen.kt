package com.bliss.blissandroidchallenge.presentation.avatarList

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bliss.blissandroidchallenge.presentation.components.ImageComponent
import com.bliss.blissandroidchallenge.presentation.components.LoaderComponent

@Composable
fun AvatarListScreen(
    modifier: Modifier = Modifier,
    viewModel: AvatarListViewModel = hiltViewModel()
) {
    val state by remember(viewModel) { viewModel.state }.collectAsState()

    EmojiListScreenContent(
        modifier = modifier,
        data = state,
        onAvatarClick = { viewModel.deleteEmoji(it) },
        onClearError = { viewModel.clearError() }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EmojiListScreenContent(
    modifier: Modifier = Modifier,
    data: AvatarListState,
    onAvatarClick: (Int) -> Unit,
    onClearError: () -> Unit
) {

    val context = LocalContext.current
    LaunchedEffect(data.error) {
        if (!data.error.isNullOrEmpty()) {
            Toast.makeText(context, data.error, Toast.LENGTH_SHORT).show()
            onClearError()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        if (data.isLoading) {

            LoaderComponent(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 75.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                itemsIndexed(data.avatarList) { _, item ->
                    ImageComponent(
                        imageURL = item.avatarUrl,
                        contentDescription = "test",
                        modifier = Modifier
                            .size(100.dp)
                            .clickable {
                                onAvatarClick(item.id)
                            }
                    )
                }
            }
        }
    }
}