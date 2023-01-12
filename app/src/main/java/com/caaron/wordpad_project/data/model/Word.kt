package com.caaron.wordpad_project.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//Declaration for database entity
@Entity
data class Word(
    // declare id as primary key
    @PrimaryKey
    val id: Long? = null,
    val title: String,
    val meaning: String,
    val synonym: String,
    val details: String,
    var status: Boolean
)
