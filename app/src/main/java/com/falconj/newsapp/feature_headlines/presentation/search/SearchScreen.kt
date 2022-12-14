package com.falconj.newsapp.feature_headlines.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.falconj.newsapp.common.RetrySection
import com.falconj.newsapp.feature_headlines.presentation.headlines.components.NewsItem
import com.falconj.newsapp.ui.theme.Purple80
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchList = viewModel.searchList.value
    val focusManager = LocalFocusManager.current

    Box {
        Column {
            TextField(
                value = viewModel.searchQuery.value,
                onValueChange = viewModel::onValueChange,
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
                    Text(
                        text = "Cancel",
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                if (viewModel.searchQuery.value.isNotEmpty()) {
                                    viewModel.onValueChange("")
                                } else {
                                    navController.navigateUp()
                                }
                            }
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        viewModel.searchEverything(viewModel.searchQuery.value)
                        focusManager.clearFocus()
                    }
                )
            )

            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(searchList.size) {

                    val item = searchList[it]
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