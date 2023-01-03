package com.xiangze.wordpadsubmission.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xiangze.wordpadsubmission.models.Word
import com.xiangze.wordpadsubmission.repository.WordRepository
import kotlin.jvm.internal.Intrinsics

class DetailsViewModel(val repo: WordRepository) : ViewModel() {
    val word = MutableLiveData<Word?>()

    fun getWordById(id: Long) {
        val res = repo.getWordById(id)
        if (res != null) {
            word.value = res
        }
    }

   class Provider(val repo: WordRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailsViewModel(repo) as T
        }
    }
}