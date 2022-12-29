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
        getWords("")
    }

    fun getWords(str: String) {
        val res = repo.getWords(str, status = true)
        words.value = res.filter { it.status }
    }

    fun sortWords(order: String, type: String, str: String) {
        var res = repo.getWords(str, true)
        if (order == "asc" && type == "title") {
            res = res.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) {
                it.title
            })
        } else if (order == "dsc" && type == "title") {
            res = res.sortedWith(compareByDescending(String.CASE_INSENSITIVE_ORDER) {
                it.title
            })
        } else if (order == "dsc" && type == "date") {
            res = res.reversed()
        }

        words.value = res.filter { it.status }
        Log.d("words", words.value.toString())
    }

    class Provider(val repo: WordRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CompletedWordsViewModel(repo) as T
        }
    }
}