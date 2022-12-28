package com.nathalie.wordpad.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nathalie.wordpad.Model.Word
import com.nathalie.wordpad.repository.WordRepository

class CompletedWordsViewModel(private val repo: WordRepository) : ViewModel() {
    val words: MutableLiveData<List<Word>> = MutableLiveData()

    init {
        getWords("", true)
    }

    fun getWords(str: String, status:Boolean) {
        val res = repo.getWords(str, status = true)
        words.value = res.filter { it.status }
        Log.d("get words", words.value.toString() + "something")
    }

    class Provider(val repo: WordRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CompletedWordsViewModel(repo) as T
        }
    }
}