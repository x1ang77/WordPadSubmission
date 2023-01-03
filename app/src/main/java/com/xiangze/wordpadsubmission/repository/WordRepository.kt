package com.xiangze.wordpadsubmission.repository

import com.xiangze.wordpadsubmission.models.Word



class WordRepository {
    private var counter: Long = 0
    private val wordsMap: MutableMap<Long, Word> = mutableMapOf(
            0L to Word(
                0,
                "Caaron is a noob",
                "What a Caa, What a Ron",
                "Yan Han",
                "Very Good England"
        )
    )
    val words: List<Word>
        get() = wordsMap.values.toList()

    fun addWord(word: Word): Word? {
        val map = wordsMap
        val j = counter + 1
        counter = j
        map[counter] = word.copy(id = counter)
        return wordsMap[counter]
    }

    fun getWordById(id: Long): Word? {
        return wordsMap[java.lang.Long.valueOf(id)]
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