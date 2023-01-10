package com.chiaching.wordpad_project.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.chiaching.wordpad_project.data.model.Word
import com.chiaching.wordpad_project.repository.WordRepository
import com.chiaching.wordpad_project.repository.WordRepositoryFake
import kotlinx.coroutines.launch

class AddWordViewModel(private val repo: WordRepository) : ViewModel() {
    fun addWord(word: Word){
        viewModelScope.launch {
            repo.addWord(word)
        }
    }

    class Provider(private val repo: WordRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T{
            return AddWordViewModel(repo) as T
        }
    }
}