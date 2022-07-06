package com.falconj.newsapp.feature_headlines.data.repository

import com.falconj.newsapp.feature_headlines.data.remote.NewsApi
import com.falconj.newsapp.feature_headlines.data.remote.dto.NewsResponse
import com.falconj.newsapp.feature_headlines.domain.repository.NewsRepository

class NewsRepositoryImpl(private val api: NewsApi) : NewsRepository {

    override suspend fun getHeadlines(
        country: String,
        page: Int,
        pageSize: Int
    ): NewsResponse {
        return api.getHeadlines(country, page, pageSize)
    }

    override suspend fun searchEverything(
        searchQuery: String,
        page: Int,
        pageSize: Int
    ): NewsResponse {
        return api.searchEverything(searchQuery, page, pageSize)
    }
}