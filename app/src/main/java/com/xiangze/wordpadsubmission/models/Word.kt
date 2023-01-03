package com.xiangze.wordpadsubmission.models


data class Word(
    val id: Long?,
    val title: String,
    val details: String,
    val meaning: String,
    val synonym: String
)