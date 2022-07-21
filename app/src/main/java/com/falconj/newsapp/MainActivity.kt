package com.falconj.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.falconj.newsapp.feature_headlines.presentation.headlines.HeadlinesScreen
import com.falconj.newsapp.feature_headlines.presentation.search.SearchScreen
import com.falconj.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "headlines"
                ) {
                    composable(route = "headlines") {
                        HeadlinesScreen(navController = navController)
                    }
                    composable(route = "searchEverything") {
                        SearchScreen(navController = navController)
                    }
                }

            }
        }
    }
}