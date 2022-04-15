package com.example.mysongs

import android.R
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mysongs.Utils.ObjectBoxUtils
import com.example.mysongs.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity(), View.OnKeyListener {

    private lateinit var binding: ActivitySongBinding
    private lateinit var clickedSong: Song
    private lateinit var editTextSongName: EditText
    private lateinit var editTextArtistName: EditText
    private lateinit var spinnerKey: Spinner
    private lateinit var saveChangesButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySongBinding = ActivitySongBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        editTextSongName = binding.editTextSongName
        editTextArtistName = binding.editTextArtistName
        spinnerKey = binding.spinnerKey
        saveChangesButton = binding.saveChangesButton

        //Übergebenen Song zuweisen
        val bundle: Bundle? = intent.extras
        bundle?.apply {
            clickedSong = bundle.get("song") as Song
        }

        //Textfelder befüllen
        editTextSongName.setText(clickedSong.title, TextView.BufferType.EDITABLE)
        editTextSongName.setOnKeyListener(this)
        editTextArtistName.setText(clickedSong.artist, TextView.BufferType.EDITABLE)
        editTextArtistName.setOnKeyListener(this)
        val mySpinner = spinnerKey
        mySpinner.adapter = ArrayAdapter<String>(
            this,
            R.layout.simple_spinner_item,
            Keys.values().map { y -> y.key }.toTypedArray()
        )
        mySpinner.setSelection(clickedSong.key!!.ordinal)

        //Spinner onItemSelectedListener
        spinnerKey.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    if(textChanged())
                        saveChangesButton.visibility = View.VISIBLE
                    else
                        saveChangesButton.visibility = View.INVISIBLE
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {}
            }

        //SaveChanges Button ausblenden und onClick festlegen
        saveChangesButton.visibility = View.INVISIBLE
        saveChangesButton.setOnClickListener {
            ObjectBoxUtils.updateDB(clickedSong,
                editTextSongName.text.toString(),
                editTextArtistName.text.toString(),
                Keys.valueOf(spinnerKey.selectedItem.toString().replace("#","s"))
            )
            ObjectBoxUtils.printDB()
        }
    }

        override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event?.action == KeyEvent.ACTION_UP) {
            //Wenn sich Text geändert hat Speicher-Button anzeigen
            if(textChanged())
                saveChangesButton.visibility = View.VISIBLE
            else
                saveChangesButton.visibility = View.INVISIBLE
            return true
        }
        return false
    }

    fun textChanged(): Boolean {
        return editTextSongName.text.toString() != clickedSong.title ||
               editTextArtistName.text.toString() != clickedSong.artist ||
               spinnerKey.selectedItemPosition != clickedSong.key?.ordinal
    }
}