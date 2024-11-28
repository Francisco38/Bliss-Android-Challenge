package com.bliss.blissandroidchallenge.presentation.googleRepoList

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
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
import com.bliss.blissandroidchallenge.presentation.components.LoaderComponent

@Composable
fun GoogleRepoListScreen(
    modifier: Modifier = Modifier,
    viewModel: GoogleRepoListViewModel = hiltViewModel()
) {
    val state by remember(viewModel) { viewModel.state }.collectAsState()

    HomeScreenContent(
        modifier = modifier,
        getMoreRepos = { viewModel.getData() },
        onClearError = { viewModel.clearError()},
        data = state
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    getMoreRepos: () -> Unit,
    onClearError: () -> Unit,
    data: RepoListState,
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
        LazyColumn{
            items(data.repoList.size) { i ->
                val item = data.repoList[i]
                if (i >= data.repoList.size - 1 && !data.endReached && !data.isLoading) {
                    getMoreRepos()
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = item.fullName
                    )
                }
            }
            item {
                if (data.isLoading) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        LoaderComponent()
                    }
                }
            }
        }
    }
}