package com.nathalie.wordpad.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nathalie.wordpad.Model.Word
import com.nathalie.wordpad.repository.WordRepository

class AddWordViewModel(private val repo: WordRepository) : ViewModel() {
    fun addWord(word: Word) {
        Log.d("get word", word.toString())
        repo.addWord(word)
    }

    class Provider(private val repo: WordRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddWordViewModel(repo) as T
        }
    }
}