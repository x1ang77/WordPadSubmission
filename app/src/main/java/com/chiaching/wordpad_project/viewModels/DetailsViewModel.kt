package com.chiaching.wordpad_project.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chiaching.wordpad_project.model.Word
import com.chiaching.wordpad_project.repository.WordRepository

class DetailsViewModel (private val repo: WordRepository): ViewModel() {
    val word: MutableLiveData<Word> = MutableLiveData()

    fun getWordById(id:Long){
        val res = repo.getWordById(id)
        res?.let{
            word.value = it
        }
    }

    fun completedWord(id :Long){
        repo.getCompletedWords(id)
    }

    fun deleteWord(id:Long){
        repo.deleteWord(id)
    }


    class Provider(val repo: WordRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailsViewModel(repo) as T
        }
    }
}