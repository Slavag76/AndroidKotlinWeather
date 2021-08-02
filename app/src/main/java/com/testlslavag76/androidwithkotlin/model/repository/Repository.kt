package com.testlslavag76.androidwithkotlin.model.repository

import com.testlslavag76.androidwithkotlin.model.data.Wheather

interface Repository {
    fun getWheatherFromServer(): Wheather
    fun getWheatherFromLocalStorage(): Wheather

}