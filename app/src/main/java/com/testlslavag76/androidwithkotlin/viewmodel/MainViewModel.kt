package com.testlslavag76.androidwithkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep

class MainViewModel(private val lieDataToObserve: MutableLiveData<Any> = MutableLiveData()) :
    ViewModel() {

    fun getData(): LiveData<Any> {
        getDataFromLocalSource()
        return lieDataToObserve
    }
private fun getDataFromLocalSource(){
    Thread{
        sleep(2000)
        lieDataToObserve.postValue(Any())
    }.start()
}
}