package com.example.mysongs

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.mysongs.Activities.MainActivity

object UserSettings {

    fun setColorTheme(context : Context, theme : Int) {
        val sharedPref = context.getSharedPreferences("settings",Context.MODE_PRIVATE)
        AppCompatDelegate.setDefaultNightMode(theme)
        sharedPref.edit().putInt("colorTheme",theme).apply()
    }
}