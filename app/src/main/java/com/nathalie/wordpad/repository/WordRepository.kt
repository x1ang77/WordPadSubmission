package com.nathalie.wordpad.repository

import com.nathalie.wordpad.data.Model.Word
import com.nathalie.wordpad.data.WordDao

class WordRepository(private val wordDao: WordDao) {
    suspend fun getWords(str: String, status: Boolean = false): List<Word> {
        return wordDao.getWords().filter {
            Regex(
                str,
                RegexOption.IGNORE_CASE
            ).containsMatchIn(it.title) && it.status == status
        }.toList()
    }

    suspend fun addWord(word: Word) {
        wordDao.insert(word)
    }

    suspend fun getWordById(id: Long): Word? {
        return wordDao.getWordById(id)
    }

    suspend fun updateWord(id: Long, word: Word) {
        wordDao.insert(word.copy(id = id))
    }

    suspend fun deleteWord(id: Long) {
        wordDao.delete(id)
    }

    suspend fun getWordByTitle(title: String): List<Word> {
        return wordDao.getWordByTitle(title)
    }

    suspend fun updateStatus(id: Long) {
        wordDao.updateStatusById(id, true)
    }
}