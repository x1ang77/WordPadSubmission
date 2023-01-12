package com.caaron.wordpad_project

import android.app.Application
import androidx.room.Room
import com.caaron.wordpad_project.data.WordDao
import com.caaron.wordpad_project.data.WordDatabase
import com.caaron.wordpad_project.repository.WordRepository

class MyApplication: Application() {
//    val wordRepoFake = WordRepository.getInstance()

    lateinit var wordRepo: WordRepository

    //It creates a Room database for storing words,
    // and an instance of the WordRepository class which handle all the interactions with the database.
    // It also has a fallbackToDestructiveMigration() method to rebuild the database if the schema changes.
    override fun onCreate() {
        super.onCreate()
        val wordDatabase = Room.databaseBuilder(
            this,
            WordDatabase::class.java,
            WordDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()

        wordRepo = WordRepository(wordDatabase.wordDao)
    }
}