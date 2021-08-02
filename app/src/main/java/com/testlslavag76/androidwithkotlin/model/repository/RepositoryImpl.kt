package com.testlslavag76.androidwithkotlin.model.repository

import com.testlslavag76.androidwithkotlin.model.data.Wheather

class RepositoryImpl: Repository {
    override fun getWheatherFromServer(): Wheather {
        return Wheather()
    }

    override fun getWheatherFromLocalStorage(): Wheather {
        return Wheather()
    }
}