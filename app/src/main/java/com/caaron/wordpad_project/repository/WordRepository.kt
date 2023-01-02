package com.caaron.wordpad_project.repository

import android.util.Log
import com.caaron.wordpad_project.model.Word
import com.caaron.wordpad_project.ui.NewWordFragment

class WordRepository {
    var counter = 0L
    val taskMap: MutableMap<Long, Word> = mutableMapOf(
//        0L to Word(0L, "Eat", "Put something in your mouth", "swallow", "hungry")
    )

    fun getWords(str: String, status: Boolean = false): List<Word> {
        return taskMap.filter { (key, value) ->
            Regex(
                str,
                RegexOption.IGNORE_CASE
            ).containsMatchIn(value.title) && value.status == status
        }.values.toList()
    }

    fun addWord(task: Word): Word? {
        taskMap[++counter] = task.copy(id = counter)
        return taskMap[counter]
    }

    fun getWordById(id: Long): Word? {
        return taskMap[id]
    }

    fun updateWord(id: Long, word: Word): Word? {
        taskMap[id] = word
        return taskMap[id]
    }

    fun deleteWord(id: Long) {
        taskMap.remove(id)
    }

    fun changeStatus(id: Long): Word? {
        taskMap[id]?.status = true
        return taskMap[id]
    }

    fun sortWord(order:String,by:String):List<Word>{
        if(order=="Ascending" && by=="Title") {
            val res = taskMap.values.toList()
            return res.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.title })
        }else if(order=="Descending" && by=="Title") {
            val res = taskMap.values.toList()
            return res.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.title }).reversed()
        }else if(order=="Descending" && by=="Date"){
            return taskMap.values.toList().reversed()
        }
        return taskMap.values.toList()
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