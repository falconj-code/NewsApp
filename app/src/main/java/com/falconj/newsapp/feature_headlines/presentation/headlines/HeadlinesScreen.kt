package com.falconj.newsapp.feature_headlines.presentation.headlines

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.falconj.newsapp.feature_headlines.presentation.headlines.components.NewsItem

@Composable
fun HeadlinesScreen(
    viewModel: HeadlinesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.headlines) { article ->
                NewsItem(
                    article = article
                )
            }
        }
    }
}