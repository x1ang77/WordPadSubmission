package com.example.wordapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.wordapp.data.WordAppDatabase
import com.example.wordapp.repository.WordRepository
import com.example.wordapp.utils.StorageService
import com.google.gson.Gson

// This is an Application class.
// It is a base class for maintaining global application state.
// You can provide your own implementation by creating a subclass and specifying the fully-qualified name of this subclass
// as the "android:name" attribute in your AndroidManifest.xml's <application> tag.
// The Application class, or your subclass of the Application class, is instantiated before any other class when the process
// for your application/package is created.
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