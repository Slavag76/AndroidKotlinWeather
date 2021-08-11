package com.testlslavag76.androidwithkotlin.model.repository

import com.testlslavag76.androidwithkotlin.model.data.Weather
import com.testlslavag76.androidwithkotlin.model.data.getRussianCities
import com.testlslavag76.androidwithkotlin.model.data.getWorldCities

class RepositoryImpl: Repository {
    override fun getWheatherFromServer() = Weather()
    override fun getWheatherFromLocalStorageRus() = getRussianCities()
    override fun getWheatherFromLocalStorageWorld() = getWorldCities()
}