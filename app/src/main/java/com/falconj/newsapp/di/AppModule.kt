package com.falconj.newsapp.di

import com.falconj.newsapp.common.Constants
import com.falconj.newsapp.feature_headlines.data.remote.NewsApi
import com.falconj.newsapp.feature_headlines.data.repository.NewsRepositoryImpl
import com.falconj.newsapp.feature_headlines.domain.repository.NewsRepository
import com.falconj.newsapp.feature_headlines.domain.use_case.GetHeadlinesUseCase
import com.falconj.newsapp.feature_headlines.domain.use_case.NewsUseCases
import com.falconj.newsapp.feature_headlines.domain.use_case.SearchEverythingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsApi): NewsRepository {
        return NewsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideNewsUseCases(repository: NewsRepository): NewsUseCases {
        return NewsUseCases(
            getHeadlinesUseCase = GetHeadlinesUseCase(repository),
            searchEverythingUseCase = SearchEverythingUseCase(repository)
        )
    }
}