package com.chiaching.wordpad_project.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.chiaching.wordpad_project.R
import com.chiaching.wordpad_project.model.SortBy
import com.chiaching.wordpad_project.model.SortKey
import com.chiaching.wordpad_project.model.Word
import com.chiaching.wordpad_project.repository.WordRepository
import com.chiaching.wordpad_project.utils.StorageService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class NewWordViewModel(val repo: WordRepository, val storageService: StorageService): ViewModel() {
    val words: MutableLiveData<List<Word>> = MutableLiveData()
    val sortBy:MutableLiveData<String> = MutableLiveData()
    val sortOrder: MutableLiveData<String> = MutableLiveData()
    val swipeRefreshLayoutfinished: MutableSharedFlow<Unit> = MutableSharedFlow()

    init{
        getWords("")
        sortBy.value = storageService.getString(SortKey.SORT_BY.name)
        sortOrder.value = storageService.getString(SortKey.SORT_ORDER.name)
    }

    fun onChangeSortBy(value: String){
        sortBy.value = value
        storageService.setString(SortKey.SORT_BY.name, value)
    }

    fun onChangeSortOrder(value: String){
        sortOrder.value = value
        storageService.setString(SortKey.SORT_ORDER.name , value)
    }

    fun onRefresh(){
        viewModelScope.launch {
            delay(3000)
            getWords("")
            swipeRefreshLayoutfinished.emit(Unit)
        }
    }

    fun getWords(str: String){
        val res = repo.getWords(str)
        words.value = res.filter{
            !it.status
        }
    }

    fun sortWords(order:String,by:String){
        val res=repo.sortWord(order,by)
        words.value=res.filter { !it.status }
    }

    class Provider(val repo: WordRepository) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewWordViewModel(repo, storageService) as T
        }
    }
}