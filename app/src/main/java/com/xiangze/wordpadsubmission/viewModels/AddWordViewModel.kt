package com.xiangze.wordpadsubmission.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.xiangze.wordpadsubmission.models.Word
import com.xiangze.wordpadsubmission.repository.WordRepository
import com.xiangze.wordpadsubmission.ui.AddWordFragment
import kotlin.jvm.internal.Intrinsics

class AddWordViewModel(private val repo: WordRepository) : ViewModel() {


    fun addWord(word: Word) {
        repo.addWord(word)
    }

    class Provider(val repo: WordRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddWordViewModel(repo) as T
        }
    }
}