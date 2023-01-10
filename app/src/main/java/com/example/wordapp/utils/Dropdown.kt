package com.example.wordapp.utils

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import com.example.wordapp.R

// object is similar to companion object in a class
object Dropdown {
    fun create(
        context: Context,
        view: AutoCompleteTextView,
        values: List<String>,
        onChange: (value: String) -> Unit
    ) {
        val adapter = ArrayAdapter<String>(
            context,
            R.layout.simple_text_item,
            values
        )

        view.setAdapter(adapter)

        view.addTextChangedListener {
            onChange(it.toString())
        }
    }
}

// This is an example of class with companion object
//class Dropdown private constructor() {
//    companion object {
//        fun create() {
//
//        }
//    }
//}