package com.caaron.wordpad_project.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.caaron.wordpad_project.model.Word
import com.caaron.wordpad_project.repository.WordRepository

class CompletedViewModel(val repo: WordRepository):ViewModel() {
    val words: MutableLiveData<List<Word>> = MutableLiveData()

    init{
        getCompletedWords("")
    }

    fun getCompletedWords(str:String){
        val res = repo.getWords(str,status = true)
        words.value = res.filter {
            it.status
        }
    }

    class Provider(val repo: WordRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CompletedViewModel(repo) as T
        }
    }
}