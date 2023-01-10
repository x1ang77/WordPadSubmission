package com.nathalie.wordpad.utils

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import com.nathalie.wordpad.R


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