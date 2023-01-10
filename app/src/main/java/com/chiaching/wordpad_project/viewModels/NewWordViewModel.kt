package com.chiaching.wordpad_project.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.chiaching.wordpad_project.data.model.Word
import com.chiaching.wordpad_project.repository.WordRepository
import kotlinx.coroutines.launch

class NewWordViewModel(val repo: WordRepository): ViewModel() {
    val words: MutableLiveData<List<Word>> = MutableLiveData()

    init {
        getWords("")
    }

    fun getWords(str: String){
        viewModelScope.launch {
            val res = repo.getWords(str)
            words.value = res.filter {
                !it.status
            }
        }
    }

    fun sortWords(order:String,by:String){
        viewModelScope.launch {
            val res = repo.sortWord(order, by)
            words.value = res.filter { !it.status }
        }
    }

    class Provider(val repo: WordRepository) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewWordViewModel(repo) as T
        }
    }
}