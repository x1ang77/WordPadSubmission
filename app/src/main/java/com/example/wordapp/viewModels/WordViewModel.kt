package com.example.wordapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wordapp.data.models.Word
import com.example.wordapp.repository.WordRepository
import kotlinx.coroutines.launch

// This is a ViewModel class that provides the Word Fragment the access to the data set and functions that are available in the repository.
class WordViewModel(private val repo: WordRepository) : ViewModel() {
    val word: MutableLiveData<Word> = MutableLiveData()

    // This is a function that returns the function to get a unit of data from the data set by ID.
    fun getWordById(id: Long) {
        viewModelScope.launch {
            val res = repo.getWordById(id)
            res?.let {
                word.value = it
            }
        }
    }

    // This is a function that returns the function to update a unit of data in the data set.
    fun changeWordStatus(id: Long, status: Boolean) {
        viewModelScope.launch {
            repo.changeWordStatus(id,status)
        }
    }

    // This is a function that returns the function to remove a unit of data from the data set.
    fun deleteWord(id: Long) {
        viewModelScope.launch {
            repo.deleteWord(id)
        }
    }

    // This a Provider class that provides Word Fragment's UI for working with the data.
    // It serves as a central repository of data where users can store and can fetch the data.
    class Provider(private val repo: WordRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return WordViewModel(repo) as T
        }
    }
}