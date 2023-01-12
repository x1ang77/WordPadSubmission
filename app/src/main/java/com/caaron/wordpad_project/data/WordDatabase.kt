package com.caaron.wordpad_project.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.caaron.wordpad_project.data.model.Word

//This class is defining an abstract database class, annotated with Room's @Database annotation,
// it provides a way to access it via the DAO.
// The class also defines a constant for the name of the database.

@Database(entities = [Word::class], version = 2)
abstract class WordDatabase : RoomDatabase() {
    abstract val wordDao: WordDao

    companion object {
        const val DATABASE_NAME = "word_database"
    }
}