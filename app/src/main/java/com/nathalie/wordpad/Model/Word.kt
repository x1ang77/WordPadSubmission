package com.nathalie.wordpad.Model

import java.text.SimpleDateFormat
import java.util.*


data class Word(
    val id: Long? = null,
    val title: String,
    val meaning: String,
    val synonym: String,
    val details: String,
    var status: Boolean = false,
    val date: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
)
