package com.testlslavag76.androidwithkotlin.model.repository

import com.testlslavag76.androidwithkotlin.model.dto.WeatherDTO


interface DetailsRepository {
    fun getWeatherDetailsFromServer(
        lat: Double,
        lon: Double,
        callback: retrofit2.Callback<WeatherDTO>
    )
}