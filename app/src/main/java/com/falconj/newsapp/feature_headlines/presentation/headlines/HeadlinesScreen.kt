package com.falconj.newsapp.feature_headlines.presentation.headlines

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.falconj.newsapp.feature_headlines.presentation.headlines.components.NewsItem
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.falconj.newsapp.ui.theme.Pink80
import com.falconj.newsapp.ui.theme.Purple40

@Composable
fun HeadlinesScreen(
    navController: NavController,
    viewModel: HeadlinesViewModel = hiltViewModel()
) {
    val everythingList by remember { viewModel.everythingNewsList }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    val endReached by remember { viewModel.endReached }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.LightGray),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "US News",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
            IconButton(onClick = { navController.navigate("searchEverything") }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
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
}






