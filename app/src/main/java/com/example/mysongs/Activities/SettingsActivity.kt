package com.example.mysongs.Activities

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.widget.SwitchCompat
import com.example.mysongs.UserSettings
import com.example.mysongs.databinding.ActivityMainBinding
import com.example.mysongs.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)

        initUI()

        val view = binding.root
        setContentView(view)
    }

    private fun initUI() {
        title = "Settings"
        val switchDarkMode = binding.switchDarkMode
        val sharedPref = getSharedPreferences("settings",Context.MODE_PRIVATE)
        val theme = sharedPref.getInt("colorTheme",1)
        if(theme != 1) switchDarkMode.toggle()
        switchDarkMode.setOnCheckedChangeListener{_, isChecked ->
            if(isChecked) {
                UserSettings.setColorTheme(this, MODE_NIGHT_YES)
            }
            if(!isChecked) {
                UserSettings.setColorTheme(this, MODE_NIGHT_NO)
            }
        }
    }


}