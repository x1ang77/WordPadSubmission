package com.caaron.wordpad_project.data

import android.icu.text.CaseMap.Title
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.caaron.wordpad_project.data.model.Word

@Dao
interface WordDao {
    @Query("SELECT * FROM word")
    suspend fun getWords(): List<Word>

    @Query("SELECT * FROM word WHERE id = :id")
    suspend fun getWordById(id: Long): Word?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word WHERE id =:id")
    suspend fun delete(id: Long)

    @Query("UPDATE word SET status = :status WHERE id = :id")
    suspend fun changeStatus(id: Long, status:Boolean)

    @Query("SELECT * FROM word WHERE title LIKE '%'|| :title ||'%'")
    suspend fun search(title: String):List<Word>
}