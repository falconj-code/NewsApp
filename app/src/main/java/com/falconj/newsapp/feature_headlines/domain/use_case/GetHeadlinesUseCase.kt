package com.falconj.newsapp.feature_headlines.domain.use_case

import com.falconj.newsapp.common.Resource
import com.falconj.newsapp.feature_headlines.data.remote.dto.NewsResponse
import com.falconj.newsapp.feature_headlines.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetHeadlinesUseCase(
    private val repository: NewsRepository
) {
    operator fun invoke(
        country: String,
        page: Int,
        pageSize: Int
    ): Flow<Resource<NewsResponse>> = flow {
        try {
            emit(Resource.Loading())
            val headlines = repository.getHeadlines(country, page, pageSize)
            emit(Resource.Success(headlines))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}