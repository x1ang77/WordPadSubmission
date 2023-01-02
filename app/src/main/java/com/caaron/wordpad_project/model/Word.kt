package com.caaron.wordpad_project.model

data class Word(
    val id: Long? = null,
    val title: String,
    val meaning: String,
    val synonym: String,
    val details: String,
    var status: Boolean
)
