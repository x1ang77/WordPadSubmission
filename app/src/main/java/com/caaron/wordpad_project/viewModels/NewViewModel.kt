package com.caaron.wordpad_project.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.caaron.wordpad_project.model.SortKey
import com.caaron.wordpad_project.model.Word
import com.caaron.wordpad_project.repository.WordRepository
import com.caaron.wordpad_project.utils.StorageService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class NewViewModel(val repo: WordRepository, val storageService: StorageService) : ViewModel() {
    val words: MutableLiveData<List<Word>> = MutableLiveData()
    val sortBy: MutableLiveData<String> = MutableLiveData()
    val sortOrder: MutableLiveData<String> = MutableLiveData()
    val swipeRefreshLayoutFinised: MutableSharedFlow<Unit> = MutableSharedFlow()

    init {
        getWords("")
        sortBy.value = storageService.getString(SortKey.SORT_BY.name)
        sortOrder.value = storageService.getString(SortKey.SORT_ORDER.name)
    }

    fun onChangeSortBy(value:String){
        sortBy.value = value
        storageService.setString(SortKey.SORT_BY.name,value)
    }

    fun onChangeSortOrder(value:String){
        sortOrder.value = value
        storageService.setString(SortKey.SORT_ORDER.name,value)
    }


    fun onRefresh() {
        viewModelScope.launch {
            delay(3000)
            getWords("")
            swipeRefreshLayoutFinised.emit(Unit)
        }
    }

    fun getWords(str: String) {
        val res = repo.getWords(str, false)
        words.value = res.filter {
            !it.status
        }
    }

    fun sortWords(order: String, by: String) {
        val res = repo.sortWord(order, by)
        words.value = res.filter { !it.status }
    }

    class Provider(val repo: WordRepository, val storageService: StorageService) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewViewModel(repo, storageService) as T
        }
    }
}