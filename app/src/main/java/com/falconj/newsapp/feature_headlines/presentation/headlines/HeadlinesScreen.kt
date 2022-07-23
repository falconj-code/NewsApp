package com.falconj.newsapp.feature_headlines.presentation.headlines

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.falconj.newsapp.feature_headlines.presentation.headlines.components.NewsItem
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.falconj.newsapp.common.RetrySection
import com.falconj.newsapp.ui.theme.Purple80
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HeadlinesScreen(
    navController: NavController,
    viewModel: HeadlinesViewModel = hiltViewModel()
) {
    val headlinesList = viewModel.headlinesList.value

    Box {
        Column {
            SmallTopAppBar(
                title = { Text(text = "US news") },
                actions = {
                    IconButton(onClick = { navController.navigate("searchEverything") }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search"
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Purple80
                )
            )
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(headlinesList.size) {
                    if (it >= headlinesList.size - 1 &&
                        !viewModel.endReached.value &&
                        !viewModel.isLoading.value
                    ) {
                        viewModel.getHeadlines()
                    }
                    val item = headlinesList[it]
                    val encodedUrl = URLEncoder.encode(item.url, StandardCharsets.UTF_8.toString())
                    NewsItem(
                        article = item,
                        onItemClick = {
                            navController.navigate("WebViewScreen/${encodedUrl}")
                        }
                    )
                }
            }
        }
        if (viewModel.isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        if (viewModel.loadError.value.isNotEmpty() && !viewModel.isLoading.value) {
            RetrySection(
                error = viewModel.loadError.value,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            ) {
                viewModel.getHeadlines()
            }
        }
    }
}






