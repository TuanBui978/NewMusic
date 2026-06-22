package com.example.splash.ui

import com.example.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel<SplashUiState, SplashUiAction, SplashUiEvent>(SplashUiState()) {
    override fun handleAction(action: SplashUiAction) {

    }
}