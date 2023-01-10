package com.example.wordapp.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wordapp.data.models.SortBy
import com.example.wordapp.data.models.SortKey
import com.example.wordapp.data.models.SortOrder
import com.example.wordapp.data.models.Word
import com.example.wordapp.repository.WordRepository
import com.example.wordapp.utils.StorageService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class AllWordsViewModel(private val repo: WordRepository, val storageService: StorageService) :
    ViewModel() {
    val words: MutableLiveData<List<Word>> = MutableLiveData()
    val sortBy: MutableLiveData<String> = MutableLiveData()
    val sortOrder: MutableLiveData<String> = MutableLiveData()
    val swipeRefreshLayoutFinished: MutableSharedFlow<Unit> = MutableSharedFlow()
    var query = ""

    init {
        getWords("")
        sortBy.value = storageService.getString(SortKey.SORT_BY.name)
        sortOrder.value = storageService.getString(SortKey.SORT_ORDER.name)
        Log.d("debugging", "${sortBy.value} and ${sortOrder.value}")
    }

    fun onChangeSortBy(value: String) {
        sortBy.value = value
        storageService.setString(SortKey.SORT_BY.name, value)
    }

    fun onChangeSortOrder(value: String) {
        sortOrder.value = value
        storageService.setString(SortKey.SORT_ORDER.name, value)
    }

    fun onSwipeRefresh() {
        viewModelScope.launch {
            delay(3000)
            getWords("")
            swipeRefreshLayoutFinished.emit(Unit)
        }
    }

    fun getWords(str: String) {
        val res = repo.getWords(str, false)
        words.value = res.filter { !it.status }
        if (sortBy.value != null && sortOrder.value != null) {
            sortWords(sortOrder.value!!, sortBy.value!!, query)
        }
    }

//    fun sortWords(order: String, category: String) {
//        val res = repo.sortWords(order, category)
//        words.value = res.filter { !it.status }
//    }

    fun sortWords(order: String, category: String, str: String) {
        var res = repo.getWords(str, false)
        if (order == SortOrder.ASC.name && category == SortBy.WORD.name) {
            res = res.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) {
                it.word
            })
        } else if (order == SortOrder.DSC.name && category == SortBy.WORD.name) {
            res = res.sortedWith(compareByDescending(String.CASE_INSENSITIVE_ORDER) {
                it.word
            })
        } else if (order == SortOrder.DSC.name && category == SortBy.DATE.name) {
            res = res.reversed()
        }
        words.value = res.filter { !it.status }
    }

    class Provider(private val repo: WordRepository, val storageService: StorageService) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AllWordsViewModel(repo, storageService) as T
        }
    }
}