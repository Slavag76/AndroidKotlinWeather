package com.testlslavag76.androidwithkotlin.model

import com.testlslavag76.androidwithkotlin.model.data.Weather

sealed class AppState {
    data class Success(val weatherData: List<Weather>) : AppState()
    class Error(val error: Throwable) : AppState()
    object  Loading: AppState()

}
