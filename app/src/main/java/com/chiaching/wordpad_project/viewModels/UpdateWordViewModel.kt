package com.chiaching.wordpad_project.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chiaching.wordpad_project.model.Word
import com.chiaching.wordpad_project.repository.WordRepository

class UpdateWordViewModel (private val repo: WordRepository): ViewModel() {
    val word: MutableLiveData<Word> = MutableLiveData()

    fun getWordById(id:Long){
        val res = repo.getWordById(id)
        res?.let{
            word.value = it
        }
    }

    fun updateWord(id: Long, word: Word){
        repo.updateWord(id, word)
    }

    fun deleteWord(id:Long){
        repo.deleteWord(id)
    }

    class Provider(val repo: WordRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UpdateWordViewModel(repo) as T
        }
    }

}