package com.chiaching.wordpad_project.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chiaching.wordpad_project.model.Word
import com.chiaching.wordpad_project.repository.WordRepository

class AddWordViewModel(private val repo: WordRepository) : ViewModel() {
    fun addWord(word:Word){
        repo.addWord(word)
    }

    class Provider(private val repo: WordRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T{
            return AddWordViewModel(repo) as T
        }
    }
}