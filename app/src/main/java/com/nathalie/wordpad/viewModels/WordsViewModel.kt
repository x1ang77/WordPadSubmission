package com.nathalie.wordpad.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nathalie.wordpad.data.Model.SortBy
import com.nathalie.wordpad.data.Model.SortKey
import com.nathalie.wordpad.data.Model.SortOrder
import com.nathalie.wordpad.data.Model.Word
import com.nathalie.wordpad.repository.WordRepository
import com.nathalie.wordpad.utils.StorageService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class WordsViewModel(val repo: WordRepository, val storageService: StorageService) : ViewModel() {
    val words: MutableLiveData<List<Word>> = MutableLiveData()
    val sortBy: MutableLiveData<String> = MutableLiveData()
    val sortOrder: MutableLiveData<String> = MutableLiveData()
    val swipeRefreshLayoutFinished: MutableSharedFlow<Unit> = MutableSharedFlow()

    init {
        getWords("")
        sortBy.value = storageService.getString(SortKey.SORT_BY.name)
        sortOrder.value = storageService.getString(SortKey.SORT_ORDER.name)
    }


    //when a radio btn is clicked, set sortBy.value to the value of that btn
    fun onChangeSortBy(value: String) {
        sortBy.value = value
        storageService.setString(SortKey.SORT_BY.name, value)
    }

    //when a radio btn is clicked, set sortOrder.value to the value of that btn
    fun onChangeSortOrder(value: String) {
        sortOrder.value = value
        storageService.setString(SortKey.SORT_ORDER.name, value)
    }

    //get words after a 3s delay
    fun onRefresh() {
        viewModelScope.launch {
            delay(3000)
            getWords("")
            swipeRefreshLayoutFinished.emit(Unit)
        }
    }

    //fetch words
    fun getWords(str: String) {
        viewModelScope.launch {
            val res = repo.getWords(str, false)
            words.value = res.filter { !it.status }
        }
    }

    //sort words according to ascending order, descending order, title and date
    fun sortWords(order: String, type: String, str: String) {
        viewModelScope.launch {
            var res = repo.getWords(str, false)
            if (order == SortOrder.ASC.name && type == SortBy.TITLE.name) {
                res = res.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) {
                    it.title
                })
            } else if (order == SortOrder.DSC.name && type == SortBy.TITLE.name) {
                res = res.sortedWith(compareByDescending(String.CASE_INSENSITIVE_ORDER) {
                    it.title
                })
            } else if (order == SortOrder.DSC.name && type == SortBy.DATE.name) {
                res = res.reversed()
            }

            words.value = res.filter { !it.status }
        }

    }

    class Provider(val repo: WordRepository, val storageService: StorageService) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return WordsViewModel(repo, storageService) as T
        }
    }
}