package com.example.mysongs

import android.app.AlertDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mysongs.databinding.ActivityMainBinding
import com.example.mysongs.databinding.AddSongDialogBinding
import com.example.statusboard.SonglistAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var newSongDialog : AlertDialog
    private lateinit var songAdapter: SonglistAdapter
    private var songList: MutableList<Song> = ArrayList()
    private val songBox = ObjectBox.store.boxFor(Song::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ObjectBox.init(this)
        songList = songBox.all
        songAdapter.notifyItemInserted(songList.size)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val recyclerView: RecyclerView = binding.recyclerView
        songAdapter = SonglistAdapter(songList)
        recyclerView.adapter = songAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        val addButton = binding.floatingActionButton;
        addButton.setOnClickListener{ newSongDialog()}
    }

    private fun newSongDialog(){
        val dialogBinding : AddSongDialogBinding = AddSongDialogBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Add Song")
        dialogBuilder.setView(dialogBinding.root)

        dialogBinding.buttonSave.setOnClickListener {
            val newSongName: String = dialogBinding.songName.text.toString().trim()
            val newSongArtist: String = dialogBinding.artistName.text.toString().trim()
            val newSongKey: Keys = Keys.valueOf(dialogBinding.spinner.selectedItem.toString().replace("#", "s"))

            if (newSongName.isNotEmpty()) {
                //create Status object and add to list
                val song = Song(songBox.count() ,newSongName, newSongArtist, newSongKey)
                songBox.put(song)
                songList.add(song)
                songAdapter.notifyItemInserted(songList.size + 1)
                newSongDialog.dismiss()
            }
        }

        val mySpinner = dialogBinding.spinner
        mySpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Keys.values().map { y -> y.key }.toTypedArray())
        newSongDialog = dialogBuilder.create()
        newSongDialog.show()
    }
}