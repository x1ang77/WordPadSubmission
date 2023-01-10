package com.nathalie.wordpad.utils

import android.content.SharedPreferences
import com.google.gson.Gson

class StorageService(private val sharedPref: SharedPreferences, private val gson: Gson) {
    fun getBoolean(key: String): Boolean {
        return sharedPref.getBoolean(key, true)
    }

    fun setBoolean(key: String, value: Boolean) {
        val editor = sharedPref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun removeBoolean(key: String, value: Boolean) {
        val editor = sharedPref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun setString(key: String, value: String) {
        val editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String): String {
        return sharedPref.getString(key, null) ?: ""
    }

    fun removeString(key: String) {
        val editor = sharedPref.edit()
        editor.remove(key)
        editor.apply()
    }

    companion object {
        private var storageService: StorageService? = null

        fun getInstance(sharedPref: SharedPreferences, gson: Gson): StorageService {
            if (storageService == null) {
                storageService = StorageService(sharedPref, gson)
            }
            return storageService!!
        }
    }
}