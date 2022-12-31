package com.chiaching.wordpad_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chiaching.wordpad_project.repository.WordRepository

class MainActivity : AppCompatActivity() {

    val wordRepo = WordRepository.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}