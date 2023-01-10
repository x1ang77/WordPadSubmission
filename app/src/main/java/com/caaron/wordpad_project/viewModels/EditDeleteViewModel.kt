package com.caaron.wordpad_project.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.caaron.wordpad_project.data.model.Word
import com.caaron.wordpad_project.repository.WordRepository
import kotlinx.coroutines.launch

class EditDeleteViewModel(private val repo: WordRepository) : ViewModel() {
    val word: MutableLiveData<Word> = MutableLiveData()

    fun getWordById(id: Long) {
        viewModelScope.launch {
            repo.getWordById(id)
            val res = repo.getWordById(id)
            res?.let {
                word.value = it
            }
        }
    }

    fun updateWord(id: Long, word: Word) {
        viewModelScope.launch {
            repo.updateWord(id, word)
        }
    }


    class Provider(val repo: WordRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EditDeleteViewModel(repo) as T
        }
    }

}