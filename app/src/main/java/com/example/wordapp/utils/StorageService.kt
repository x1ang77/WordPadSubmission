package com.example.wordapp.utils

import android.content.SharedPreferences
import com.google.gson.Gson

// This is public class that contains the functions for SharedPreferences.
class StorageService private constructor(
    private val sharedPref: SharedPreferences,
    private val gson: Gson
) {
    // This function sets the key-value pair for string data type.
    fun setString(key: String, value: String) {
        val editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    // This function gets the key-value pair for string data type.
    fun getString(key: String): String {
        return sharedPref.getString(key, null) ?: ""
    }

    // This function removes the key-value pair for string data type.
    fun removeString(key: String) {
        val editor = sharedPref.edit()
        editor.remove(key)
        editor.apply()
    }

    companion object {
        private var storageService: StorageService? = null

        // This allows the StorageService to behave as a singleton.
        fun getInstance(sharedPref: SharedPreferences, gson: Gson): StorageService {
            if (storageService == null) {
                storageService = StorageService(sharedPref, gson)
            }

            return storageService!!
        }
    }
}