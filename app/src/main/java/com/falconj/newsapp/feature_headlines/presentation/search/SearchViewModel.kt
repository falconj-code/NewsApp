package com.falconj.newsapp.feature_headlines.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falconj.newsapp.common.Constants.PAGE_SIZE
import com.falconj.newsapp.common.Resource
import com.falconj.newsapp.feature_headlines.data.remote.dto.Article
import com.falconj.newsapp.feature_headlines.domain.use_case.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    private var curPage = 1

    private val _searchList = mutableStateOf<List<Article>>(listOf())
    val searchList: State<List<Article>> = _searchList

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _searchLoadError = mutableStateOf("")
    val searchLoadError: State<String> = _searchLoadError

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private var searchJob: Job? = null

    fun searchEverything(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            newsUseCases.searchEverythingUseCase(query, curPage, PAGE_SIZE)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {

                            _searchList.value = result.data?.articles ?: emptyList()
                            _searchLoadError.value = ""
                            _isLoading.value = false
                        }
                        is Resource.Error -> {
                            _searchLoadError.value = result.message!!
                            _isLoading.value = false
                        }
                        is Resource.Loading -> {
                            _isLoading.value = true
                        }
                    }
                }.launchIn(this)
        }
    }

    fun onValueChange(query: String) {
        _searchQuery.value = query
    }

}








