package com.example.mysongs.Songs

import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.mysongs.Activities.MainActivity
import com.example.mysongs.Activities.SongActivity
import com.example.mysongs.Enums.Keys
import com.example.mysongs.databinding.ActivitySongBinding


class SongGuitar(id: Long,
                  title: String? = null,
                  artist: String? = null,
                  key: Keys? = null) : Song(id, title, artist, key) {

    private val textView: TextView = TextView(SongActivity())

    override fun showGUI(binding: ActivitySongBinding){

        // Create TextView programmatically.
        textView.layoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        textView.gravity = Gravity.CENTER
        textView.text = "Ich bin ein SongGuitar"
        textView.setOnClickListener {
            Toast.makeText(MainActivity(), "IT WORKED BABY!!!", Toast.LENGTH_LONG).show()
        }
        binding.root.addView(textView)
        binding.root.refreshDrawableState()
    }
}