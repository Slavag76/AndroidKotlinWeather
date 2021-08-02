package com.testlslavag76.androidwithkotlin.model.data

data class Wheather(
    val city: City = getDeaultCity(),
    val temperature: Int = 0,
    val feelslike: Int = 0,
)

fun getDeaultCity() = City("Москва", 55.5578, 37.61729)