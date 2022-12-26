package com.nathalie.wordpad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nathalie.wordpad.repository.WordRepository

class MainActivity : AppCompatActivity() {
    val wordRepo = WordRepository.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}