package com.nathalie.wordpad.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nathalie.wordpad.data.Model.Word

@Dao
interface WordDao {
    @Query("SELECT * FROM word")
    suspend fun getWords(): List<Word>

    @Query("SELECT * FROM word where id= :id")
    suspend fun getWordById(id: Long): Word?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM word WHERE title = :title2")
    suspend fun getWordByTitle(title2: String): List<Word>

    @Query("UPDATE word SET status = :status WHERE id = :id")
    suspend fun updateStatusById(id: Long, status: Boolean)
}