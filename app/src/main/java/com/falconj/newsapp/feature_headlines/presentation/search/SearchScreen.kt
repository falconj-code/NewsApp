package com.falconj.newsapp.feature_headlines.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.falconj.newsapp.common.RetrySection
import com.falconj.newsapp.feature_headlines.presentation.headlines.components.NewsItem
import com.falconj.newsapp.feature_headlines.presentation.search.SearchViewModel
import com.falconj.newsapp.ui.theme.Pink80
import com.falconj.newsapp.ui.theme.Purple80
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchList = viewModel.searchList.value

    Box {
        Column {
            TextField(
                value = viewModel.searchQuery.value,
                onValueChange = viewModel::searchEverything,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                placeholder = {
                    Text(text = "Search...")
                },
                singleLine = true,
                leadingIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search"
                        )
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Purple80
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if (viewModel.searchQuery.value.isNotEmpty()) {
                                viewModel.searchEverything("")
                            } else {
                                navController.navigateUp()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close"
                        )
                    }
                }
            )


            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(searchList.size) {
                    if (it >= searchList.size - 1 &&
                        !viewModel.endReached.value &&
                        !viewModel.isLoading.value
                    ) {
                        viewModel.searchEverything(viewModel.searchQuery.value)
                    }
                    NewsItem(article = searchList[it])

                }
            }
        }
        if (viewModel.isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        if (viewModel.searchLoadError.value.isNotEmpty() &&
            !viewModel.isLoading.value
        ) {
            RetrySection(
                error = viewModel.searchLoadError.value,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            ) {
                viewModel.searchEverything(viewModel.searchQuery.value)
            }
        }
    }

}