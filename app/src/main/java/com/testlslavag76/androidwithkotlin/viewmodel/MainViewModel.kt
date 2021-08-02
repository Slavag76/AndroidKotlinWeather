package com.testlslavag76.androidwithkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.testlslavag76.androidwithkotlin.model.AppState
import com.testlslavag76.androidwithkotlin.model.repository.Repository
import com.testlslavag76.androidwithkotlin.model.repository.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(private val repository: Repository = RepositoryImpl()) :
    ViewModel() {

    private val lieDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private var counter: Int = 0

    fun getData(): LiveData<AppState> {

        return lieDataToObserve
    }


    fun getWheatherFromLocalStorage() {
        lieDataToObserve.value = AppState.Loading
        Thread {
            sleep(2000)
            counter++
            lieDataToObserve.postValue(AppState.Success(repository.getWheatherFromLocalStorage()))
        }.start()
    }

    fun getWheatherFromServer() {
        lieDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            counter++
            lieDataToObserve.postValue(AppState.Success(repository.getWheatherFromServer()))
        }.start()
    }
}
