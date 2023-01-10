package com.nathalie.wordpad.data.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class Word(
    @PrimaryKey
    val id: Long? = null,
    val title: String,
    val meaning: String,
    val synonym: String,
    val details: String,
    var status: Boolean = false,
    val date: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
)
