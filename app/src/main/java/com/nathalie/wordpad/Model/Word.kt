package com.nathalie.wordpad.Model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate


data class Word (
    val id: Long? = null,
    val title: String,
    val meaning: String,
    val synonym: String,
    val details: String,
    var status: Boolean = false,
)
