package com.example.wordapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wordapp.data.models.SortCategory
import com.example.wordapp.data.models.SortKey
import com.example.wordapp.data.models.SortOrder
import com.example.wordapp.data.models.Word
import com.example.wordapp.repository.WordRepository
import com.example.wordapp.utils.StorageService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

// This is a ViewModel class that provides the All Words Fragment the access to the data set and functions that are available in the repository.
class AllWordsViewModel(
    private val repo: WordRepository,
    private val storageService: StorageService
) :
    ViewModel() {
    val words: MutableLiveData<List<Word>> = MutableLiveData()
    val sortCategory: MutableLiveData<String> = MutableLiveData()
    val sortOrder: MutableLiveData<String> = MutableLiveData()
    val swipeRefreshLayoutFinished: MutableSharedFlow<Unit> = MutableSharedFlow()
    val emptyQuery = ""

    init {
        getWords(emptyQuery)
        sortCategory.value = storageService.getString(SortKey.SORT_CATEGORY.name)
        sortOrder.value = storageService.getString(SortKey.SORT_ORDER.name)
    }

    // This is a function that sets the key-value pair from the sortCategory to the SharedPreferences.
    fun onChangeSortBy(value: String) {
        sortCategory.value = value
        storageService.setString(SortKey.SORT_CATEGORY.name, value)
    }

    // This is a function that sets the key-value pair from the sortOrder to the SharedPreferences.
    fun onChangeSortOrder(value: String) {
        sortOrder.value = value
        storageService.setString(SortKey.SORT_ORDER.name, value)
    }

    // This is a function that returns the function to get all data from the data set when swiping down.
    fun onSwipeRefresh() {
        viewModelScope.launch {
            delay(3000)
            getWords("")
            swipeRefreshLayoutFinished.emit(Unit)
        }
    }

    // This is a function that returns the function to get all data from the data set.
    fun getWords(str: String) {
        viewModelScope.launch {
            val res = repo.getWords(str)
            words.value = res.filter { !it.status }
            if (sortCategory.value != null && sortOrder.value != null) {
                sortWords(sortOrder.value!!, sortCategory.value!!, str)
            }
        }
    }

    // This is a function that returns the function to get all data from the data set when sorting.
    fun sortWords(order: String, category: String, str: String) {
        viewModelScope.launch {
            var res = repo.getWords(str)
            if (order == SortOrder.ASC.name && category == SortCategory.WORD.name) {
                res = res.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) {
                    it.word
                })
            } else if (order == SortOrder.DSC.name && category == SortCategory.WORD.name) {
                res = res.sortedWith(compareByDescending(String.CASE_INSENSITIVE_ORDER) {
                    it.word
                })
            } else if (order == SortOrder.DSC.name && category == SortCategory.DATE.name) {
                res = res.reversed()
            }
            words.value = res.filter { !it.status }
        }
    }

    // This a Provider class that provides All Words Fragment's UI for working with the data.
    // It serves as a central repository of data where users can store and can fetch the data.
    class Provider(private val repo: WordRepository, private val storageService: StorageService) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AllWordsViewModel(repo, storageService) as T
        }
    }
}