package com.testlslavag76.androidwithkotlin.model.repository

import com.testlslavag76.androidwithkotlin.model.data.Weather

interface Repository {
    fun getWheatherFromServer(): Weather
    fun getWheatherFromLocalStorageRus(): List<Weather>
    fun getWheatherFromLocalStorageWorld(): List<Weather>

}