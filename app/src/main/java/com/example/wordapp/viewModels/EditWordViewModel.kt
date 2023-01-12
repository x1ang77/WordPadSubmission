package com.example.wordapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wordapp.data.models.Word
import com.example.wordapp.repository.WordRepository
import kotlinx.coroutines.launch

// This is a ViewModel class that provides the Edit Word Fragment the access to the data set and functions that are available in the repository.
class EditWordViewModel(private val repo: WordRepository): ViewModel() {
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

    // This is a function that returns the function to replace an existing unit of data in the data set.
    fun editWord(id: Long, word: Word) {
        viewModelScope.launch {
            repo.editWord(id, word)
        }
    }

    // This a Provider class that provides Edit Word Fragment's UI for working with the data.
    // It serves as a central repository of data where users can store and can fetch the data.
    class Provider(private val repo: WordRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EditWordViewModel(repo) as T
        }
    }
}