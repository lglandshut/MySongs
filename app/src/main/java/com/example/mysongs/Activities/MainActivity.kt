package com.example.mysongs.Activities

import android.app.AlertDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.mysongs.Enums.Keys
import com.example.mysongs.Enums.SongTypes
import com.example.mysongs.Songs.Song
import com.example.mysongs.Utils.ObjectBoxUtils
import com.example.mysongs.Utils.SongListUtils
import com.example.mysongs.databinding.ActivityMainBinding
import com.example.mysongs.databinding.AddSongDialogBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var newSongDialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        ObjectBoxUtils.init(this)
        SongListUtils.init(this, binding)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.myToolbar)
        val addButton = binding.floatingActionButton;
        addButton.setOnClickListener{ newSongDialog()}
    }

    override fun onResume() {
        super.onResume()
        SongListUtils.refreshList()
    }

    private fun newSongDialog(){

        val dialogBinding : AddSongDialogBinding = AddSongDialogBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Add Song")
        dialogBuilder.setView(dialogBinding.root)

        dialogBinding.buttonSave.setOnClickListener {
            val newSongName: String = dialogBinding.songName.text.toString().trim()
            val newSongArtist: String = dialogBinding.artistName.text.toString().trim()
            val newSongKey: Keys = Keys.valueOf(dialogBinding.spinnerKey.selectedItem.toString().replace("#", "s"))
            val newSongType: SongTypes = SongTypes.valueOf(dialogBinding.spinnerType.selectedItem.toString())

            if (newSongName.isNotEmpty()) {
                //create Status object and add to list
                val song = Song(0 ,newSongName, newSongArtist, newSongKey, newSongType)
                SongListUtils.addSong(song)
                ObjectBoxUtils.printDB()
                newSongDialog.dismiss()
            }
        }

        val myKeySpinner = dialogBinding.spinnerKey
        myKeySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Keys.values().map { y -> y.key }.toTypedArray())
        val myTypeSpinner = dialogBinding.spinnerType
        myTypeSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, SongTypes.values().map { y -> y.name }.toTypedArray())
        newSongDialog = dialogBuilder.create()
        newSongDialog.show()
    }

}