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

    fun getWords(): List<Word> {
        return wordsMap.values.toList()
    }

    fun addWord(word: Word): Word? {
        wordsMap[++counter] = word.copy(id = counter)
        return wordsMap[counter]
    }

    fun getWordById(id: Long): Word? {
        return wordsMap[id]
    }

    fun updateWord(id: Long, note: Word): Word? {
        wordsMap[id] = note
        return wordsMap[id]
    }

    fun deleteWord(id: Long) {
        wordsMap.remove(id)
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