package com.falconj.newsapp.feature_headlines.domain.use_case

import com.falconj.newsapp.common.Resource
import com.falconj.newsapp.feature_headlines.data.remote.dto.NewsResponse
import com.falconj.newsapp.feature_headlines.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

//class SearchEverythingUseCase(
//    private val repository: NewsRepository
//) {
//    operator fun invoke(
//        searchQuery: String,
//        pageNumber: Int
//    ): Flow<Resource<List<NewsResponse>>> = flow {
//        try {
//            emit(Resource.Loading())
//            val searchEverything = repository.searchEverything(searchQuery, pageNumber)
//            emit(Resource.Success(searchEverything))
//        } catch (e: HttpException) {
//            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
//        } catch (e: IOException) {
//            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
//        }
//    }
//}