package com.example.wordapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// This is a ViewModel class that provides the Home Fragment the access to the data set and functions that are available in the repository.
class HomeViewModel: ViewModel() {
    val refreshAllWords: MutableLiveData<Boolean> = MutableLiveData(false)
    val refreshCompletedWords: MutableLiveData<Boolean> = MutableLiveData(false)

    // This is a function that returns the MutableLiveData that is a boolean data type.
    fun doRefreshAllWords(refresh: Boolean) {
        refreshAllWords.value = refresh
    }

    // This is a function that returns the MutableLiveData that is a boolean data type.
    fun doRefreshCompletedWords(refresh: Boolean) {
        refreshCompletedWords.value = refresh
    }
}