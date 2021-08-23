package com.testlslavag76.androidwithkotlin.model.repository

import com.testlslavag76.androidwithkotlin.model.data.Weather

interface LocalRepository {
    fun getAllHistory(): List<Weather>
    fun saveEntity(weather: Weather)
}