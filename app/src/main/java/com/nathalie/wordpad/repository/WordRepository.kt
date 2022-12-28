package com.nathalie.wordpad.repository

import android.util.Log
import com.nathalie.wordpad.Model.Word

class WordRepository {
    private var counter = 0L
    private val wordsMap: MutableMap<Long, Word> = mutableMapOf(
        0L to Word(
            0L,
            "Metanoia",
            "The journey of changing one's word, heart or a way ot life.",
            "something",
            "something"
        )
    )

    fun getWords(str: String, status:Boolean = false): List<Word> {
        return wordsMap.filter { (key, value) -> Regex(str).containsMatchIn(value.title) && value.status == status }.values.toList()
    }

    fun addWord(word: Word): Word? {
        wordsMap[++counter] = word.copy(id = counter)
        return wordsMap[counter]
    }

    fun getWordById(id: Long): Word? {
        return wordsMap[id]
    }

    fun updateWord(id: Long, word: Word): Word? {
        wordsMap[id] = word
        return wordsMap[id]
    }

    fun deleteWord(id: Long) {
        wordsMap.remove(id)
    }

    fun changeStatus(id: Long): Word? {
        wordsMap[id]?.status = !wordsMap[id]?.status!!
        return wordsMap[id]
    }

    companion object {
        var wordRepository: WordRepository? = null

        fun getInstance(): WordRepository {
            if (wordRepository == null) {
                wordRepository = WordRepository()
            }

            return wordRepository!!
        }
    }
}