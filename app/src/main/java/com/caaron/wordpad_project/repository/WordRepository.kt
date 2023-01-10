package com.caaron.wordpad_project.repository

import android.util.Log
import com.caaron.wordpad_project.data.WordDao
import com.caaron.wordpad_project.data.model.Word
import com.caaron.wordpad_project.ui.NewWordFragment

class WordRepository(private val wordDao: WordDao) {

    suspend fun getWords(title:String): List<Word> {
        if(title == ""){
        return wordDao.getWords()
        }
        return wordDao.search(title)
    }

    suspend fun addWord(word: Word) {
        wordDao.insert(word)
    }

    suspend fun getWordById(id: Long): Word? {
        return wordDao.getWordById(id)
    }

    suspend fun updateWord(id: Long,word: Word){
        wordDao.insert(word.copy(id = id))
    }

    suspend fun deleteWord(id: Long){
        wordDao.delete(id)
    }

    suspend fun changeStatus(id: Long){
        wordDao.changeStatus(id,true)
    }

    suspend fun sortWord(order:String, by: String):List<Word>{
        if(order=="Ascending" && by=="Title") {
            val res = wordDao.getWords()
            return res.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.title })
        }else if(order=="Descending" && by=="Title") {
            val res = wordDao.getWords()
            return res.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.title }).reversed()
        }else if(order=="Descending" && by=="Date"){
            return wordDao.getWords().reversed()
        }
        return wordDao.getWords()
    }
}