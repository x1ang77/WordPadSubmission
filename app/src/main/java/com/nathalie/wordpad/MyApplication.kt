package com.nathalie.wordpad

import android.app.Application
import com.nathalie.wordpad.repository.WordRepository

class MyApplication : Application() {
    val wordRepo = WordRepository.getInstance()
}