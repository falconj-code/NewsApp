package com.falconj.newsapp.feature_headlines.presentation.headlines

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.falconj.newsapp.feature_headlines.data.remote.dto.Article
import com.falconj.newsapp.feature_headlines.presentation.headlines.components.NewsItem

@Composable
fun HeadlinesScreen(
    viewModel: HeadlinesViewModel = hiltViewModel()
) {
//    val state = viewModel.state.value

    val everythingList by remember { viewModel.everythingNewsList }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    val endReached by remember { viewModel.endReached }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(everythingList.size) {
            if (it >= everythingList.size - 1 && !endReached && !isLoading) {
                viewModel.getHeadlines()
            }
            NewsItem(
                article = everythingList[it]
            )
        }
    }

}