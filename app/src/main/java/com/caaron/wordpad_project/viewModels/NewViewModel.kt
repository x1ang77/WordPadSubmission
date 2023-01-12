package com.caaron.wordpad_project.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.caaron.wordpad_project.data.model.Word
import com.caaron.wordpad_project.repository.WordRepository
import kotlinx.coroutines.launch

class NewViewModel(val repo: WordRepository):ViewModel() {
    val words:MutableLiveData<List<Word>> = MutableLiveData()

    init{
        getWords("")
    }

    //This function provide to getWord Fragment's UI the access to data from repository
    fun getWords(str:String){
        viewModelScope.launch{
        val res = repo.getWords(str)
        words.value = res.filter {
            !it.status
        }
        }
    }

    //This function provide to sortWord Fragment's UI the access to data from repository
     fun sortWord(order:String, by:String){
         viewModelScope.launch {
             val res = repo.sortWord(order, by)
             words.value = res.filter { !it.status }
         }
    }

    class Provider(val repo:WordRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewViewModel(repo) as T
        }
    }
}