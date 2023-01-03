package com.xiangze.wordpadsubmission

import android.app.Application
import com.xiangze.wordpadsubmission.repository.WordRepository

class MyApplication : Application() {
    val wordRepo: WordRepository = WordRepository.getInstance()
}