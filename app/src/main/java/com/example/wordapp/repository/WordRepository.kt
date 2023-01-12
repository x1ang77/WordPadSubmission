package com.example.wordapp.repository

import com.example.wordapp.data.WordDao
import com.example.wordapp.data.models.Word

// try private constructor to see if it still works
class WordRepository(private val wordDao: WordDao) {
    suspend fun getWords(str: String): List<Word> {
        if (str == "") {
            return wordDao.getWords()
        }
        return wordDao.getWordsBySearch(str)
    }

    suspend fun getWordById(id: Long): Word? {
        return wordDao.getWordById(id)
    }

    suspend fun addWord(word: Word) {
        wordDao.insert(word)
    }

    suspend fun changeWordStatus(id: Long, status: Boolean) {
        wordDao.updateStatusById(id, status)
    }

    suspend fun editWord(id: Long, word: Word) {
        wordDao.insert(word.copy(id = id))
    }

    suspend fun deleteWord(id: Long) {
        wordDao.delete(id)
    }

//    fun getWords(str: String, status: Boolean = false): List<Word> {
//        return wordMap.filter { (key, value) ->
//            Regex(
//                str,
//                RegexOption.IGNORE_CASE
//            ).containsMatchIn(value.word) && value.status == status
//        }.values.toList()
//    }
}