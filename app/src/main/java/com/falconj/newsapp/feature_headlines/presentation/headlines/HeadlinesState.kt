package com.falconj.newsapp.feature_headlines.presentation.headlines

import com.falconj.newsapp.feature_headlines.data.remote.dto.Article
import com.falconj.newsapp.feature_headlines.data.remote.dto.NewsResponse

data class HeadlinesState(
    var isLoading: Boolean = false,
    var headlines: List<Article> = emptyList(),
    var error: String = "",
    var endReached: Boolean = false
)
