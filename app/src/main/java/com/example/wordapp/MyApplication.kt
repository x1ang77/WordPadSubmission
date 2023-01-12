package com.example.wordapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.wordapp.data.WordAppDatabase
import com.example.wordapp.repository.WordRepository
import com.example.wordapp.utils.StorageService
import com.google.gson.Gson

class MyApplication : Application() {
    lateinit var wordRepo: WordRepository
    lateinit var storageService: StorageService

    override fun onCreate() {
        super.onCreate()

        val wordAppDatabase = Room.databaseBuilder(
            this,
            WordAppDatabase::class.java,
            WordAppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
        wordRepo = WordRepository(wordAppDatabase.wordDao)

        val name: String = this.packageName ?: throw NullPointerException("No package name found")
        storageService = StorageService.getInstance(
            this.getSharedPreferences(name, Context.MODE_PRIVATE),
            Gson()
        )
    }
}

// MVVM, Clean Architecture
// inheritance