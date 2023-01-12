package com.caaron.wordpad_project.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.caaron.wordpad_project.data.model.Word
import com.caaron.wordpad_project.repository.WordRepository
import kotlinx.coroutines.launch

class CompletedViewModel(val repo: WordRepository) : ViewModel() {
    val words: MutableLiveData<List<Word>> = MutableLiveData()

    init {
        getCompletedWords("")
    }

    //This function provide to completedWord Fragment's UI the access to data from repository
    fun getCompletedWords(str: String) {
        viewModelScope.launch {
            val res = repo.getWords(str)
            words.value = res.filter {
                it.status
            }
        }
    }

    class Provider(val repo: WordRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CompletedViewModel(repo) as T
        }
    }
}