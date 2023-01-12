package com.example.wordapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wordapp.data.models.Word

@Dao
interface WordDao {
    // This suspend function holds the query for getting all data from the data set.
    @Query("SELECT * FROM word")
    suspend fun getWords(): List<Word>

    // This suspend function holds the query for getting a unit of data from the data set by the ID.
    @Query("SELECT * FROM word WHERE id = :id")
    suspend fun getWordById(id: Long): Word?

    // This suspend function holds the method for inserting a new unit or replacing an existing unit of data to the data set.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word)

    // This suspend function holds the query for deleting a unit of data from the data set by the ID.
    @Query("DELETE FROM word WHERE id = :id")
    suspend fun delete(id: Long)

    // This suspend function holds the query for updating a unit of data from the data set by the ID.
    @Query("UPDATE word SET status = :status WHERE id = :id")
    suspend fun updateStatusById(id: Long, status: Boolean)

    // This suspend function holds the query for getting a unit of data from the data set by the Regex.
    @Query("SELECT * FROM word WHERE word LIKE '%'|| :word ||'%'")
    suspend fun getWordsBySearch(word: String): List<Word>
}