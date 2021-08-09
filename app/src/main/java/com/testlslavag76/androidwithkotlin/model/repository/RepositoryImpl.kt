package com.testlslavag76.androidwithkotlin.model.repository

import com.testlslavag76.androidwithkotlin.model.data.Weather
import com.testlslavag76.androidwithkotlin.model.data.getRussianCities
import com.testlslavag76.androidwithkotlin.model.data.getWorldCities

class RepositoryImpl: Repository {
    override fun getWheatherFromServer(): Weather {
        return Weather()
    }

    override fun getWheatherFromLocalStorageRus(): List<Weather> {
        return getRussianCities()
    }

    override fun getWheatherFromLocalStorageWorld(): List<Weather> {
        return getWorldCities()
    }

}