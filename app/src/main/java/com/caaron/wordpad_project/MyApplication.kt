package com.caaron.wordpad_project

import android.app.Application
import android.content.Context
import com.caaron.wordpad_project.repository.WordRepository
import com.caaron.wordpad_project.utils.StorageService
import com.google.gson.Gson

class MyApplication: Application() {
    val wordRepo = WordRepository.getInstance()
    val name: String = this.packageName ?: throw NullPointerException("No package name found")
    val storageService = StorageService.getInstance(
        this.getSharedPreferences(name,Context.MODE_PRIVATE),
        Gson()
    )
}