package com.testlslavag76.androidwithkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.testlslavag76.androidwithkotlin.app.App.Companion.getHistoryDao
import com.testlslavag76.androidwithkotlin.model.AppState
import com.testlslavag76.androidwithkotlin.model.repository.LocalRepository
import com.testlslavag76.androidwithkotlin.model.repository.LocalRepositoryImpl

class HistoryViewModel(
    val historyLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao())
) : ViewModel() {

    fun getAllHistory() {
        historyLiveData.value = AppState.Loading
        historyLiveData.value = AppState.Success(historyRepository.getAllHistory())
    }
}