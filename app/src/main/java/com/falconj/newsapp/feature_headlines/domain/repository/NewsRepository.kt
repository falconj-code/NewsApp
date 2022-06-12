package com.falconj.newsapp.feature_headlines.domain.repository

import com.falconj.newsapp.feature_headlines.data.remote.dto.NewsResponse

interface NewsRepository {

    suspend fun getHeadlines(countryCode: String, pageNumber: Int): NewsResponse

    suspend fun searchEverything(searchQuery: String, pageNumber: Int): NewsResponse
}