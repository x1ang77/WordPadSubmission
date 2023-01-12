package com.example.wordapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// This is a AppCompatActivity class.
// It is a base class for activities that wish to use some of the newer platform features on older Android devices.
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}