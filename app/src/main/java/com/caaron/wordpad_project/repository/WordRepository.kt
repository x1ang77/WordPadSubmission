package com.caaron.wordpad_project.repository

import android.util.Log
import com.caaron.wordpad_project.data.WordDao
import com.caaron.wordpad_project.data.model.Word
import com.caaron.wordpad_project.ui.NewWordFragment
//This class is to handle data operations such as fetching, inserting,
//updating, and deleting of data from a data source.
class WordRepository(private val wordDao: WordDao) {

    //This function is to return a list of words from the database,
    //by searching for words with a specific title.
    suspend fun getWords(title:String): List<Word> {
        if(title == ""){
        return wordDao.getWords()
        }
        return wordDao.search(title)
    }

    //This function take a single parameter word and it,
    //add the word to the database
    suspend fun addWord(word: Word) {
        wordDao.insert(word)
    }

    //This function take a single parameter id and to get the word by id
    suspend fun getWordById(id: Long): Word? {
        return wordDao.getWordById(id)
    }

    //This function take atwo parameters id and word to updates the word from database
    suspend fun updateWord(id: Long,word: Word){
        wordDao.insert(word.copy(id = id))
    }

    //This function take a single parameter id and it delete the word from the database
    suspend fun deleteWord(id: Long){
        wordDao.delete(id)
    }

    //This function take a single parameter id and it change the status by boolean of th eword
    suspend fun changeStatus(id: Long){
        wordDao.changeStatus(id,true)
    }

    //This function take two parameter order and by to sorts the word based on order and by
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