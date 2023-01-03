package com.xiangze.wordpadsubmission.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xiangze.wordpadsubmission.models.Word
import com.xiangze.wordpadsubmission.repository.WordRepository
import kotlin.jvm.internal.Intrinsics

class HomeViewModel(val repo: WordRepository) : ViewModel() {
    val words = MutableLiveData<List<Word>>()

    init {
        getAllWords()
    }

    fun getAllWords() {
        words.value = repo.words
    }

    class Provider(val repo: WordRepository) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            Intrinsics.checkNotNullParameter(modelClass, "modelClass")
            return HomeViewModel(repo) as T
        }
    }
}