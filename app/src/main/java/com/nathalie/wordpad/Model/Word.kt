package com.nathalie.wordpad.Model

data class Word(
    val id: Long? = null,
    val title: String,
    val meaning: String,
    val synonym: String,
    val details: String
)