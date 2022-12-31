package com.chiaching.wordpad_project.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chiaching.wordpad_project.R
import com.chiaching.wordpad_project.model.Word
import com.chiaching.wordpad_project.repository.WordRepository

class NewWordViewModel(val repo: WordRepository): ViewModel() {
    val words: MutableLiveData<List<Word>> = MutableLiveData()

    init{
        getWords("")
    }

    fun getWords(str: String){
        val res = repo.getWords(str)
        words.value = res.filter{
            !it.status
        }
    }

    fun sortWords(order:String,by:String){
        val res=repo.sortWord(order,by)
        words.value=res.filter { !it.status }
    }

    class Provider(val repo: WordRepository) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewWordViewModel(repo) as T
        }
    }
}