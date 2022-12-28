package com.nathalie.wordpad.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nathalie.wordpad.Model.Word
import com.nathalie.wordpad.repository.WordRepository

class DetailsViewModel(private val repo: WordRepository) : ViewModel() {
    val word: MutableLiveData<Word> = MutableLiveData()

    fun getWordById(id: Long) {
        val res = repo.getWordById(id)
        res?.let {
            word.value = it
        }
    }

    fun deleteWord(id: Long) {
        repo.deleteWord(id)
    }

    fun changeStatus(id: Long) {
        repo.changeStatus(id)
    }

    class Provider(val repo: WordRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailsViewModel(repo) as T
        }
    }
}