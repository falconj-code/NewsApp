package com.falconj.newsapp.feature_headlines.presentation.headlines

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falconj.newsapp.common.Constants.COUNTRY
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
class HeadlinesViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    private var curPage = 1

    private val _headlinesList = mutableStateOf<List<Article>>(listOf())
    val headlinesList: State<List<Article>> = _headlinesList

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _endReached = mutableStateOf(false)
    val endReached: State<Boolean> = _endReached

    private val _loadError = mutableStateOf("")
    val loadError: State<String> = _loadError

    init {
        getHeadlines()
    }

    fun getHeadlines() {
        newsUseCases.getHeadlinesUseCase(COUNTRY, curPage, PAGE_SIZE).onEach {
            _isLoading.value = true
            when (it) {
                is Resource.Success -> {
                    _endReached.value = curPage * PAGE_SIZE >= it.data!!.totalResults
                    val headlines = it.data.articles

                    curPage++

                    _loadError.value = ""
                    _isLoading.value = false
                    _headlinesList.value += headlines
                }
                is Resource.Error -> {
                    _loadError.value = it.message!!
                    _isLoading.value = false
                }
                is Resource.Loading -> {
                    _isLoading.value = true
                }
            }
        }.launchIn(viewModelScope)
    }
}








