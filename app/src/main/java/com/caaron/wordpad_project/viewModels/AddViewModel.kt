package com.caaron.wordpad_project.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.caaron.wordpad_project.model.Word
import com.caaron.wordpad_project.repository.WordRepository

class AddViewModel(private val repo: WordRepository) : ViewModel() {
    fun addWord(word: Word) {
        repo.addWord(word)
    }

    class Provider(val repo: WordRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddViewModel(repo) as T
        }
    }
}