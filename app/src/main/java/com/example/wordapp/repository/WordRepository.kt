package com.example.wordapp.repository

import com.example.wordapp.data.WordDao
import com.example.wordapp.data.models.Word

// try private constructor to see if it still works
class WordRepository {
//    suspend fun getWords(): List<Word> {
//        return wordDao.getWords()
//    }



    private var counter = -1L
    private val wordMap: MutableMap<Long, Word> = mutableMapOf()

    fun getWords(str: String, status: Boolean = false): List<Word> {
        return wordMap.filter { (key, value) ->
            Regex(
                str,
                RegexOption.IGNORE_CASE
            ).containsMatchIn(value.word) && value.status == status
        }.values.toList()
    }

    fun getWordById(id: Long): Word? {
        return wordMap[id]
    }

    fun addWord(word: Word): Word? {
        wordMap[++counter] = word.copy(id = counter)
        return wordMap[counter]
    }

    fun changeWordStatus(id: Long): Word? {
        wordMap[id]?.status = !wordMap[id]?.status!!
        return wordMap[id]
    }

    fun editWord(id: Long, word: Word): Word? {
        wordMap[id] = word
        return wordMap[id]
    }

    fun deleteWord(id: Long) {
        wordMap.remove(id)
    }

    companion object {
        private var wordRepository: WordRepository? = null
        fun getInstance(): WordRepository {
            if (wordRepository == null) {
                wordRepository = WordRepository()
            }

            return wordRepository!!
        }
    }
}