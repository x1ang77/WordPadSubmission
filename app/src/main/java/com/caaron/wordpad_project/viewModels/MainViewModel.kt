package com.caaron.wordpad_project.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val refreshWords: MutableLiveData<Boolean> = MutableLiveData(false)
    val refreshCompletedWords: MutableLiveData<Boolean> = MutableLiveData(false)


    // Return the mutable live data type that is boolean
    fun shouldRefreshWords(refresh:Boolean){
        refreshWords.value = refresh
    }

    // Return the mutable live data type that is boolean
    fun shouldRefreshCompletedWords(refresh: Boolean){
        refreshCompletedWords.value = refresh
    }
}