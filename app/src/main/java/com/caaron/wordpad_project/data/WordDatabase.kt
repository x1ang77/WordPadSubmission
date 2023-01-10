package com.caaron.wordpad_project.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.caaron.wordpad_project.data.model.Word

@Database(entities = [Word::class], version = 2)
abstract class WordDatabase : RoomDatabase() {
    abstract val wordDao: WordDao

    companion object {
        const val DATABASE_NAME = "word_database"
    }
}