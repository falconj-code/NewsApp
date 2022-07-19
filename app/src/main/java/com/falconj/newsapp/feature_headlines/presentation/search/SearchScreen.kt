package com.falconj.newsapp.feature_headlines.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.falconj.newsapp.feature_headlines.presentation.headlines.components.NewsItem
import com.falconj.newsapp.feature_headlines.presentation.search.SearchViewModel
import com.falconj.newsapp.ui.theme.Pink80

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchList = viewModel.searchList.value

    val searchLoadError by remember { viewModel.searchLoadError }
    val searchIsLoading by remember { viewModel.searchIsLoading }
    val searchEndReached by remember { viewModel.searchEndReached }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.LightGray),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = viewModel.searchQuery.value,
                onValueChange = viewModel::searchEverything,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.LightGray),
                placeholder = {
                    Text(text = "Search...")
                },
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(searchList.size) {
                if (it >= searchList.size - 1 && !searchEndReached && !searchIsLoading) {
                    viewModel.searchEverything(viewModel.searchQuery.value)
                }
                NewsItem(article = searchList[it])

            }
        }
    }
}