package com.nathalie.wordpad.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nathalie.wordpad.data.Model.Word


@Database(entities = [Word::class], version = 2)
abstract class WordPadDatabase : RoomDatabase() {
    abstract val wordDao: WordDao

    companion object {
        const val DATABASE_NAME = "wordpad_database"
    }
}


