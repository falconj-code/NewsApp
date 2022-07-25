package com.falconj.newsapp.feature_headlines.presentation.web_view

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf("")
    val state: State<String> = _state

    init {
        savedStateHandle.get<String>("articleUrl")?.let { url ->
            webView(url)
        }
    }

    private fun webView(url: String) {
        _state.value = url
    }
}





