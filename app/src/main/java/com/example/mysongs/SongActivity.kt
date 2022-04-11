package com.example.mysongs

import android.R
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mysongs.databinding.ActivityBoardBinding

class SongActivity : AppCompatActivity(), View.OnKeyListener {

    private lateinit var binding: ActivityBoardBinding
    var clickedSong: Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Übergebenen Song zuweisen
        val bundle: Bundle? = intent.extras
        bundle?.apply {
            clickedSong = bundle.get("song") as? Song
        }

        //Textfelder befüllen
        if (clickedSong != null) {
            binding.editTextSongName.setText(clickedSong!!.title, TextView.BufferType.EDITABLE)
            binding.editTextSongName.setOnKeyListener(this)
            binding.editTextArtistName.setText(clickedSong!!.artist, TextView.BufferType.EDITABLE)
            binding.editTextArtistName.setOnKeyListener(this)
            val mySpinner = binding.spinnerKey
            mySpinner.adapter = ArrayAdapter<String>(
                this,
                R.layout.simple_spinner_item,
                Keys.values().map { y -> y.key }.toTypedArray()
            )
            mySpinner.setSelection(clickedSong?.key!!.ordinal)

            //Spinner onItemSelectedListener
            binding.spinnerKey.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parentView: AdapterView<*>?,
                        selectedItemView: View?,
                        position: Int,
                        id: Long
                    ) {
                        if(textChanged())
                            binding.saveChangesButton.visibility = View.VISIBLE
                        else
                            binding.saveChangesButton.visibility = View.INVISIBLE
                    }

                    override fun onNothingSelected(parentView: AdapterView<*>?) {
                        // your code here
                    }
                }

            //SaveChanges Button ausblenden und onClick festlegen
            binding.saveChangesButton.visibility = View.INVISIBLE
            binding.saveChangesButton.setOnClickListener {
                //TODO: Datenbankeinträge updaten
            }
        }
    }

        override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event?.action == KeyEvent.ACTION_UP) {
            //Wenn sich Text geändert hat Speicher-Button anzeigen
            if(textChanged())
                binding.saveChangesButton.visibility = View.VISIBLE
            else
                binding.saveChangesButton.visibility = View.INVISIBLE
            return true
        }
        return false
    }

    fun textChanged(): Boolean {
        return binding.editTextSongName.text.toString() != clickedSong?.title ||
               binding.editTextArtistName.text.toString() != clickedSong?.artist ||
               binding.spinnerKey.selectedItemPosition != clickedSong?.key?.ordinal
    }
}