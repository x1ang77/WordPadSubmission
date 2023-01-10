package com.nathalie.wordpad

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.nathalie.wordpad.data.WordPadDatabase
import com.nathalie.wordpad.repository.WordRepository
import com.nathalie.wordpad.utils.StorageService

class MyApplication : Application() {
//    val wordRepo = WordRepositoryFake.getInstance()
    lateinit var storageService: StorageService

    lateinit var wordRepo: WordRepository
    override fun onCreate() {
        super.onCreate()

        val wordPadDatabase = Room.databaseBuilder(
            this,
            WordPadDatabase::class.java,
            WordPadDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

        wordRepo = WordRepository(wordPadDatabase.wordDao)

        val name: String = this.packageName ?: throw NullPointerException("No package name")
        storageService = StorageService.getInstance(
            this.getSharedPreferences(name, Context.MODE_PRIVATE),
            Gson()
        )
    }

}