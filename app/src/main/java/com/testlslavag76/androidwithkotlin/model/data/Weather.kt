package com.testlslavag76.androidwithkotlin.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    val city: City = getDeaultCity(),
    val temperature: Int = 0,
    val feelslike: Int = 0,
    val condition: String = "sunny"
) : Parcelable

fun getDeaultCity() = City("Москва", 55.5578, 37.61729)

