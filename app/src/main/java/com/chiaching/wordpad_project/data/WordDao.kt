package com.chiaching.wordpad_project.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chiaching.wordpad_project.data.model.Word

@Dao
interface WordDao {
    @Query("SELECT * FROM word")
    suspend fun getWords(): List<Word>

    @Query("SELECT * FROM word WHERE title LIKE '%'|| :title ||'%'")
    suspend fun getWordsBySearch(title: String): List<Word>

    @Query("SELECT * from word WHERE id = :id")
    suspend fun getWordById(id: Int): Word?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(id: Word)

    @Query("DELETE FROM word WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("UPDATE word SET status = :status WHERE id = :id")
    suspend fun completedWord(id:Long,status:Boolean)

}