package com.chiaching.wordpad_project.repository

import com.chiaching.wordpad_project.data.WordDao
import com.chiaching.wordpad_project.data.model.Word

class WordRepository(private val wordDao: WordDao) {

    suspend fun getWords(str: String): List<Word> {
        if(str == ""){
            return wordDao.getWords()
        }
        return wordDao.getWordsBySearch(str)
    }

    suspend fun addWord(task: Word){
        wordDao.insert(task)
    }

    suspend fun getWordById(id: Int) : Word?{
        return wordDao.getWordById(id)
    }

    suspend fun updateWord(id: Long, word: Word){
        wordDao.insert(word.copy(id.toInt()))
    }

    suspend fun deleteWord(id:Int){
        wordDao.delete(id)
    }

    suspend fun getCompletedWords(id:Int){
        wordDao.completedWord(id.toLong(), true)
    }

    suspend fun sortWord(order:String,by:String):List<Word>{
        if(order=="Ascending" && by=="Title") {
            val res = wordDao.getWords()
            return res.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.title })
        }else if(order=="Descending" && by=="Title") {
            val res = wordDao.getWords()
            return res.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.title }).reversed()
        }else if(order=="Descending" && by=="Date") {
            return wordDao.getWords().reversed()
        }
        return wordDao.getWords()
    }
}