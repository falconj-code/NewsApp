package com.falconj.newsapp.feature_headlines.presentation.headlines

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falconj.newsapp.common.Resource
import com.falconj.newsapp.feature_headlines.domain.use_case.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HeadlinesViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    private val _state = mutableStateOf(HeadlinesState())
    val state: State<HeadlinesState> = _state

    private val countryCode: String = "us"
    private val pageNumber: Int = 1

    init {
        getHeadlines()
    }

    private fun getHeadlines() {
        newsUseCases.getHeadlinesUseCase(countryCode, pageNumber).onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = HeadlinesState(
                        headlines = it.data?.articles ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _state.value = HeadlinesState(
                        error = it.message ?: "An unexpected error occurred."
                    )
                }
                is Resource.Loading -> {
                    _state.value = HeadlinesState(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}