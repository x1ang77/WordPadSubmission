package com.caaron.wordpad_project

import android.app.Application
import com.caaron.wordpad_project.repository.WordRepository

class MyApplication: Application() {
    val wordRepo = WordRepository.getInstance()
}