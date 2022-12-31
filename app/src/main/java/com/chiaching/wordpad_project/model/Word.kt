package com.chiaching.wordpad_project.model

data class Word(
    val id: Long? = null,
    val title: String,
    val meaning: String,
    val synonyms: String,
    val details: String,
    var status: Boolean
)
