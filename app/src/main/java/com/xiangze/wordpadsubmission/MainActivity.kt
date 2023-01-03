package com.xiangze.wordpadsubmission

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xiangze.wordpadsubmission.R
import com.xiangze.wordpadsubmission.repository.WordRepository
import kotlin.jvm.internal.Intrinsics

class MainActivity : AppCompatActivity() {
    var wordRepo: WordRepository = WordRepository.getInstance()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getPermission()
    }

    private fun getPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(arrayOf("android.permission.READ_EXTERNAL_STORAGE"), 1)
        }
    }
}