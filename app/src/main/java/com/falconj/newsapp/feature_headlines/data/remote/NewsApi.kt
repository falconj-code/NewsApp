package com.falconj.newsapp.feature_headlines.data.remote

import com.falconj.newsapp.common.Constants.API_KEY
import com.falconj.newsapp.feature_headlines.data.remote.dto.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("/v2/top-headlines")
    suspend fun getHeadlines(
        @Query("country")
        country: String = "us",
        @Query("page")
        page: Int = 1,
        @Query("pageSize")
        pageSize: Int = 20,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): NewsResponse

    @GET("/v2/everything")
    suspend fun searchEverything(
        @Query("q")
        searchQuery: String,
        @Query("page")
        page: Int = 1,
        @Query("pageSize")
        pageSize: Int = 20,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): NewsResponse
}
