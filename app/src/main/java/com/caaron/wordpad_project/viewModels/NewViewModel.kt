package com.caaron.wordpad_project.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.caaron.wordpad_project.model.Word
import com.caaron.wordpad_project.repository.WordRepository

class NewViewModel(val repo: WordRepository):ViewModel() {
    val words:MutableLiveData<List<Word>> = MutableLiveData()

    init{
        getWords("")
    }

    fun getWords(str:String){
        val res = repo.getWords(str,false)
        words.value = res.filter {
            !it.status
        }
    }

    fun sortWords(order:String,by:String){
        val res=repo.sortWord(order,by)
        words.value=res.filter { !it.status}
    }

    class Provider(val repo:WordRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewViewModel(repo) as T
        }
    }
}