package com.caaron.wordpad_project.data

import android.icu.text.CaseMap.Title
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.caaron.wordpad_project.data.model.Word

@Dao
interface WordDao {

    //This function is to get all words in database
    @Query("SELECT * FROM word")
    suspend fun getWords(): List<Word>

    //This function is to get single word by targeting id in database
    @Query("SELECT * FROM word WHERE id = :id")
    suspend fun getWordById(id: Long): Word?

    //This function is to update word and replace old details in database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word)

    //This function is to delete word in database
    @Query("DELETE FROM word WHERE id =:id")
    suspend fun delete(id: Long)

    //This function is to change the word status from boolean in database
    @Query("UPDATE word SET status = :status WHERE id = :id")
    suspend fun changeStatus(id: Long, status:Boolean)

    //This function is to search the word by title in database
    @Query("SELECT * FROM word WHERE title LIKE '%'|| :title ||'%'")
    suspend fun search(title: String):List<Word>
}