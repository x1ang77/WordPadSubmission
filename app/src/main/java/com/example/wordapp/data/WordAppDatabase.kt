package com.example.wordapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wordapp.data.models.Word

@Database(entities = [Word::class], version = 1)
abstract class WordAppDatabase : RoomDatabase() {
    abstract val wordDao: WordDao

    companion object {
        const val DATABASE_NAME = "word_app_database"
    }
}