package com.example.mysongs.Activities

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import com.example.mysongs.Enums.Keys
import com.example.mysongs.Enums.SongTypes
import com.example.mysongs.R
import com.example.mysongs.Songs.Song
import com.example.mysongs.Utils.ObjectBoxUtils
import com.example.mysongs.Utils.SongListUtils
import com.example.mysongs.databinding.ActivityMainBinding
import com.example.mysongs.databinding.AddSongDialogBinding
import org.geeksforgeeks.gfgmodalsheet.ModalBottomSheet
import java.security.Key

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var newSongDialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        ObjectBoxUtils.init(this)
        SongListUtils.init(this, binding)
        val view = binding.root
        setContentView(view)
        initUI()
    }

    override fun onResume() {
        super.onResume()
        ObjectBoxUtils.printCompDB()
        SongListUtils.refreshList()
    }

    private fun openAddSongDialog(){

        val dialogBinding : AddSongDialogBinding = AddSongDialogBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Add Song")
        dialogBuilder.setView(dialogBinding.root)
        dialogBinding.buttonSave.setOnClickListener {
            val newSongName: String = dialogBinding.songName.text.toString().trim()
            var newSongArtist: String? = dialogBinding.artistName.text.toString().trim()
            if(newSongArtist.isNullOrEmpty()) newSongArtist = null

            var keyValue = dialogBinding.spinnerKey.selectedItem.toString()
            val newSongKey: Keys = if(keyValue.isEmpty()) Keys.NONE else Keys.valueOf(keyValue.replace("#", "s"))
            val newSongType: SongTypes = SongTypes.valueOf(dialogBinding.spinnerType.selectedItem.toString())

            if (newSongName.isNotEmpty()) {
                //create Status object and add to list
                val song = Song(0 ,newSongName, newSongArtist, newSongKey, newSongType)
                SongListUtils.addSong(song)
                ObjectBoxUtils.printSongDB()
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

    private fun initUI() {
        val toolbar = binding.toolBar
        //toolbar.setLogo(R.drawable.app_icon)
        setSupportActionBar(toolbar)
        val buttonAddNewSong = binding.buttonAddNewSong;
        buttonAddNewSong.setOnClickListener { openAddSongDialog() }
        val sortButton = binding.buttonSort
        sortButton.setOnClickListener {
            val modalBottomSheet = ModalBottomSheet()
            modalBottomSheet.show(
                supportFragmentManager,
                ModalBottomSheet.TAG
            )
        }
        val buttonMore = binding.buttonMore
        buttonMore.setOnClickListener { v: View ->
            showMenu(v, R.menu.overflow_menu) }
        }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(v.context, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            if (menuItem.title == "Fretbo.ar") {
                val uri: Uri = Uri.parse("https://fretbo.ar")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            true
        }
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
    }
}