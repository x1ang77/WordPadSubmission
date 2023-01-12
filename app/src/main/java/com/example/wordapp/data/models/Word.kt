package com.example.wordapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


// This data class holds the data model for Word
@Entity
data class Word(
    @PrimaryKey
    val id: Long? = null,
    val word: String,
    val meaning: String,
    val synonym: String,
    val example: String,
    val date: String,
    var status: Boolean = false
)
