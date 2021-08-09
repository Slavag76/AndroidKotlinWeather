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


    fun getData(): LiveData<AppState> {

        return lieDataToObserve
    }


    fun getWheatherFromLocalStorageRus() = getDataFromLocalsource(isRussia = true)

    fun getWheatherFromLocalStorageWorld() = getDataFromLocalsource(isRussia= false)


    private fun getDataFromLocalsource(isRussia: Boolean) {
        lieDataToObserve.value = AppState.Loading
        Thread {
            sleep(3000)
            lieDataToObserve.postValue(
                AppState.Success(
                    if (isRussia)
                        repository.getWheatherFromLocalStorageRus()
                    else repository.getWheatherFromLocalStorageWorld()
                )
            )
        }.start()
    }
}
