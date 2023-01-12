package com.nathalie.wordpad.repository

import com.nathalie.wordpad.data.Model.Word
import com.nathalie.wordpad.data.WordDao

class WordRepository(private val wordDao: WordDao) {

    //fetch words that matches the filter is there's any and status. true of false
    suspend fun getWords(str: String, status: Boolean = false): List<Word> {
        return wordDao.getWords().filter {
            Regex(
                str,
                RegexOption.IGNORE_CASE
            ).containsMatchIn(it.title) && it.status == status
        }.toList()
    }

    //add word
    suspend fun addWord(word: Word) {
        wordDao.insert(word)
    }

    //fetch one word by id
    suspend fun getWordById(id: Long): Word? {
        return wordDao.getWordById(id)
    }


    //find one by id and update it
    suspend fun updateWord(id: Long, word: Word) {
        wordDao.insert(word.copy(id = id))
    }

    //delete one word by id
    suspend fun deleteWord(id: Long) {
        wordDao.delete(id)
    }

    //find word by title
    suspend fun getWordByTitle(title: String): List<Word> {
        return wordDao.getWordByTitle(title)
    }

    //update a word's status
    suspend fun updateStatus(id: Long) {
        wordDao.updateStatusById(id, true)
    }
}