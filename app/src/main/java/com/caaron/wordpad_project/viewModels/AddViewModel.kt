package com.caaron.wordpad_project.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.caaron.wordpad_project.data.model.Word
import com.caaron.wordpad_project.repository.WordRepository
import kotlinx.coroutines.launch

class AddViewModel(private val repo: WordRepository) : ViewModel() {
    fun addWord(word: Word) {
        viewModelScope.launch {
            repo.addWord(word)
        }
    }

    class Provider(val repo: WordRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddViewModel(repo) as T
        }
    }
}