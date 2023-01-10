package com.chiaching.wordpad_project

import android.app.Application
import androidx.room.Room
import com.chiaching.wordpad_project.data.WordListDatabase
import com.chiaching.wordpad_project.repository.WordRepository

class MyApplication : Application() {
    lateinit var wordRepo: WordRepository

    override fun onCreate() {
        super.onCreate()
        val wordListDatabase = Room.databaseBuilder(
            this,
            WordListDatabase::class.java,
            WordListDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

        wordRepo = WordRepository(wordListDatabase.wordDao)
    }

}