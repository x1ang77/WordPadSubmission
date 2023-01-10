package com.example.wordapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wordapp.data.models.Word

@Dao
interface WordDao {
    @Query("SELECT * FROM word")
    suspend fun getWords(): List<Word>

    @Query("SELECT * FROM word WHERE id = :id")
    suspend fun getWordById(id: Long): Word?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM word WHERE word LIKE :word")
    suspend fun getWordByWord(word: String): List<Word>
}