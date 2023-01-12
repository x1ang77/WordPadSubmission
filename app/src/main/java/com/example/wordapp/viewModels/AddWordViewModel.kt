package com.example.wordapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wordapp.data.models.Word
import com.example.wordapp.repository.WordRepository
import kotlinx.coroutines.launch

// This is a ViewModel class that provides the Add Word Fragment the access to the data set and functions that are available in the repository.
class AddWordViewModel(private val repo: WordRepository) : ViewModel() {
    // This is a function that returns the function to insert a new unit of data into the data set.
    fun addWord(word: Word) {
        viewModelScope.launch {
            repo.addWord(word)
        }
    }

    // This a Provider class that provides Add Word Fragment's UI for working with the data.
    // It serves as a central repository of data where users can store and can fetch the data.
    class Provider(private val repo: WordRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddWordViewModel(repo) as T
        }
    }
}