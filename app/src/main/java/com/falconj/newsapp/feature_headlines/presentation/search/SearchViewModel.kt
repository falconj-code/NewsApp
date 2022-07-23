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
import kotlinx.coroutines.delay
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

    private val _endReached = mutableStateOf(false)
    val endReached: State<Boolean> = _endReached


    private val _searchLoadError = mutableStateOf("")
    val searchLoadError: State<String> = _searchLoadError

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private var searchJob: Job? = null

    fun searchEverything(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            newsUseCases.searchEverythingUseCase(query, curPage, PAGE_SIZE)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _endReached.value = curPage * PAGE_SIZE >= result.data!!.totalResults
                            val search = result.data.articles

                            curPage++

                            _searchLoadError.value = ""
                            _isLoading.value = false
                            _searchList.value += search
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


}







