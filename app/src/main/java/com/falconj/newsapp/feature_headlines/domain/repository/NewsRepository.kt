package com.falconj.newsapp.feature_headlines.domain.repository

import com.falconj.newsapp.feature_headlines.data.remote.dto.NewsResponse

interface NewsRepository {

    suspend fun getHeadlines(
        country: String,
        page: Int,
        pageSize: Int
    ): NewsResponse

    suspend fun searchEverything(
        searchQuery: String,
        page: Int,
        pageSize: Int
    ): NewsResponse
}