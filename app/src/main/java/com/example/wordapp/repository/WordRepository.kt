package com.example.wordapp.repository

import com.example.wordapp.data.WordDao
import com.example.wordapp.data.models.Word

// try private constructor to see if it still works
// This is a repository class.
// This repository isolates the data sources, such as a Room database, from the rest of the app.
// This repository provides a clean API for data access to the rest of the app.
class WordRepository(private val wordDao: WordDao) {
    // This suspend function returns the suspend function to get a unit of data from the data set by the ID.
    suspend fun getWords(str: String): List<Word> {
        if (str == "") {
            return wordDao.getWords()
        }
        return wordDao.getWordsBySearch(str)
    }

    // This suspend function returns the suspend function to get a unit of data from the data set by the ID.
    suspend fun getWordById(id: Long): Word? {
        return wordDao.getWordById(id)
    }

    // This suspend function returns the suspend function to insert a new unit of data to the data set.
    suspend fun addWord(word: Word) {
        wordDao.insert(word)
    }

    // This suspend function returns the suspend function to update a unit of data from the data set by the ID.
    suspend fun changeWordStatus(id: Long, status: Boolean) {
        wordDao.updateStatusById(id, status)
    }

    // This suspend function returns the suspend function to replace an existing unit of data to the data set.
    suspend fun editWord(id: Long, word: Word) {
        wordDao.insert(word.copy(id = id))
    }

    // This suspend function returns the suspend function to delete a unit of data from the data set by the ID.
    suspend fun deleteWord(id: Long) {
        wordDao.delete(id)
    }
}