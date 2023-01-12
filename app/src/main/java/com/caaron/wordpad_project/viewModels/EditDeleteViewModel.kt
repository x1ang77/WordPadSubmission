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

    //This function provide to getWordById Fragment's UI the access to data from repository
    fun getWordById(id: Long) {
        viewModelScope.launch {
            repo.getWordById(id)
            val res = repo.getWordById(id)
            res?.let {
                word.value = it
            }
        }
    }

    //This function provide to updateWord Fragment's UI the access to data from repository
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