package com.caaron.wordpad_project.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.caaron.wordpad_project.data.model.Word
import com.caaron.wordpad_project.repository.WordRepository
import kotlinx.coroutines.launch

class DetailsViewModel(private val repo: WordRepository) : ViewModel() {
    val word: MutableLiveData<Word> = MutableLiveData()

    fun getWordById(id: Long) {
        viewModelScope.launch{
        val res = repo.getWordById(id)
        res?.let {
            word.value = it
        }
        }
    }

    fun changeStatus(id: Long) {
        viewModelScope.launch{
        repo.changeStatus(id)
        }
    }

    fun deleteWord(id:Long){
        viewModelScope.launch{
        repo.deleteWord(id)
        }
    }

    class Provider(val repo: WordRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailsViewModel(repo) as T
        }
    }
}