package com.falconj.newsapp.feature_headlines.presentation.headlines

import com.falconj.newsapp.feature_headlines.data.remote.dto.Article
import com.falconj.newsapp.feature_headlines.data.remote.dto.NewsResponse

data class HeadlinesState(
    val isLoading: Boolean = false,
    val headlines: List<Article> = emptyList(),
    val error: String = ""
)
