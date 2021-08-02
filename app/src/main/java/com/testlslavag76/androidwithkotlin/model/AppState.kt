package com.testlslavag76.androidwithkotlin.model

import com.testlslavag76.androidwithkotlin.model.data.Wheather

sealed class AppState {
    data class Success(val weatherData: Wheather) : AppState()
    class Error(val error: Throwable) : AppState()
    object  Loading: AppState()

}
