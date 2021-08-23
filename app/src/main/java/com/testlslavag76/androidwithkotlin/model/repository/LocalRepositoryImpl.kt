package com.testlslavag76.androidwithkotlin.model.repository

import com.testlslavag76.androidwithkotlin.model.data.City
import com.testlslavag76.androidwithkotlin.model.data.Weather
import com.testlslavag76.androidwithkotlin.room.HistoryDao
import com.testlslavag76.androidwithkotlin.room.HistoryEntity

class LocalRepositoryImpl(private val localDataSource: HistoryDao) :
    LocalRepository {

    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun saveEntity(weather: Weather) {
        localDataSource.insert(convertWeatherToEntity(weather))
    }
}

fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>): List<Weather> {
    return entityList.map {
        Weather(City(it.city, 0.0, 0.0), it.temperature, 0, it.condition)
    }
}

fun convertWeatherToEntity(weather: Weather): HistoryEntity {
    return HistoryEntity(0, weather.city.city, weather.temperature, weather.condition)
}
