package com.caaron.wordpad_project

import android.app.Application
import androidx.room.Room
import com.caaron.wordpad_project.data.WordDao
import com.caaron.wordpad_project.data.WordDatabase
import com.caaron.wordpad_project.repository.WordRepository

class MyApplication: Application() {
//    val wordRepoFake = WordRepository.getInstance()

    lateinit var wordRepo: WordRepository

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