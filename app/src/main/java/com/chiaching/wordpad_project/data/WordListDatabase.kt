package com.chiaching.wordpad_project.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chiaching.wordpad_project.data.model.Word

@Database(entities = [Word::class], version = 1)
abstract class WordListDatabase: RoomDatabase() {
    abstract val wordDao: WordDao

    companion object{
        const val DATABASE_NAME = "word_list_database"
    }
}