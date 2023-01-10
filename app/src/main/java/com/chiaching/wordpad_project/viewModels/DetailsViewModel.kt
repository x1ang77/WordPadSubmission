package com.chiaching.wordpad_project.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.chiaching.wordpad_project.data.model.Word
import com.chiaching.wordpad_project.repository.WordRepository
import com.chiaching.wordpad_project.repository.WordRepositoryFake
import kotlinx.coroutines.launch

class DetailsViewModel (private val repo: WordRepository): ViewModel() {
    val word: MutableLiveData<Word> = MutableLiveData()

    fun getWordById(id:Long){
        viewModelScope.launch {
            val res = repo.getWordById(id.toInt())
            res?.let {
                word.value = it
            }
        }
    }

//    fun completedWord(id :Long){
//        viewModelScope.launch {
//            repo.getCompletedWords(id)
//        }
//    }

    fun deleteWord(id:Long){
        viewModelScope.launch {
            repo.deleteWord(id.toInt())
        }
    }

    fun completedWord(id: Long) {
        viewModelScope.launch {
            repo.getCompletedWords(id.toInt())
        }
    }


    class Provider(val repo: WordRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailsViewModel(repo) as T
        }
    }
}