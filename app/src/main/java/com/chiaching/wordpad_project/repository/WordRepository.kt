package com.chiaching.wordpad_project.repository

import com.chiaching.wordpad_project.R
import com.chiaching.wordpad_project.model.Word

class WordRepository {

    var counter = 0L
    var wordMap: MutableMap<Long, Word> = mutableMapOf()

    fun getWords(str: String): List<Word> {
        return wordMap.filter { (key ,value) -> Regex(str, RegexOption.IGNORE_CASE).containsMatchIn(value.title) }.values.toList()
    }

    fun getCompletedWords(id:Long): Word?{
        wordMap[id]?.status = true
        return wordMap[id]
    }

    fun sortWord(order:String,by:String):List<Word>{
        if(order=="Ascending" && by=="Title") {
            val res = wordMap.values.toList()
            return res.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.title })
        }else if(order=="Descending" && by=="Title") {
            val res = wordMap.values.toList()
            return res.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.title }).reversed()
        }else if(order=="Descending" && by=="Date"){
            return wordMap.values.toList().reversed()
        }
        return wordMap.values.toList()
    }

    fun addWord(word: Word): Word?{
        wordMap[++counter] = word.copy(id = counter)
        return wordMap[counter]
    }

    fun getWordById(id: Long) : Word?{
        return wordMap[id]
    }

    fun updateWord(id:Long, Word: Word): Word?{
        wordMap[id] = Word
        return wordMap[id]
    }

    fun deleteWord(id:Long){
        wordMap.remove(id)
    }

    companion object{
        private var wordRepository: WordRepository? = null

        fun getInstance(): WordRepository{
            if(wordRepository == null){
                wordRepository = WordRepository()
            }
            return wordRepository!!
        }
    }
}