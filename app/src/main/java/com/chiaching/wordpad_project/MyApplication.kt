package com.chiaching.wordpad_project

import android.app.Application
import com.chiaching.wordpad_project.repository.WordRepository

class MyApplication : Application() {
    val wordRepo = WordRepository.getInstance()
}