package com.example.mysongs.Activities

import android.R
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.URLUtil
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mysongs.Enums.Keys
import com.example.mysongs.Enums.SongTypes
import com.example.mysongs.Songs.Song
import com.example.mysongs.Utils.ObjectBoxUtils
import com.example.mysongs.databinding.*


class SongActivity : AppCompatActivity(), View.OnKeyListener, View.OnFocusChangeListener {

    private lateinit var clickedSong: Song
    private lateinit var editTextSongName: EditText
    private lateinit var editTextArtistName: EditText
    private lateinit var spinnerKey: Spinner
    private lateinit var saveChangesButton: Button
    private lateinit var editLinkButton: ImageButton
    private lateinit var openLinkButton: Button
    private lateinit var binding: ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        editTextSongName = binding.editTextSongName
        editTextArtistName = binding.editTextArtistName
        spinnerKey = binding.spinnerKey
        saveChangesButton = binding.saveChangesButton
        editLinkButton = binding.buttonEditLink
        openLinkButton = binding.buttonLink

        //Aus Intent den übergebenen Song zuweisen
        val bundle: Bundle? = intent.extras
        bundle?.apply {
            clickedSong = bundle.get("song") as Song
        }

        initUI()
        showTypeLayout() //Je nach Song.type das entsprechende Layout anzeigen
    }

    /**
     *  Bei einem onKey event den saveChangesButton anzeigen
     */
    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event?.action == KeyEvent.ACTION_UP) {
            //Wenn sich Text geändert hat Speicher-Button anzeigen
            if(textChanged()) saveChangesButton.visibility = View.VISIBLE
            else saveChangesButton.visibility = View.INVISIBLE
            return true
        }
        return false
    }

    /**
     * Bei einem onFocusChange Event den saveChangesButton anzeigen
     */
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if(textChanged()) saveChangesButton.visibility = View.VISIBLE
        else saveChangesButton.visibility = View.INVISIBLE
    }

    /**
     * Überprüfen ob ungespeicherte Änderungen vorliegen, wenn BackButton gedrückt wird
     */
    override fun onBackPressed() {
        if(textChanged()){
            showSaveChangesDialog()
        }
        else finish()
    }

    /**
     * Vergleicht die Eingabe mit dem Song-Objekt
     */
    fun textChanged(): Boolean {
        return editTextSongName.text.toString() != clickedSong.title ||
                editTextArtistName.text.toString() != clickedSong.artist ||
                spinnerKey.selectedItemPosition != clickedSong.key?.ordinal
    }

    /**
     *  UI befüllen und Listener festlegen
     */
    private fun initUI(){
        //Textfelder befüllen
        editTextSongName.setText(clickedSong.title, TextView.BufferType.EDITABLE)
        editTextSongName.setOnKeyListener(this)
        editTextSongName.onFocusChangeListener = this
        editTextArtistName.setText(clickedSong.artist, TextView.BufferType.EDITABLE)
        editTextArtistName.setOnKeyListener(this)
        editTextArtistName.onFocusChangeListener = this
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

        //EditURI Dialog anzeigen wenn onClick auf EditButton
        editLinkButton.setOnClickListener { editURIDialog()}

        //OpenURI
        openLinkButton.setOnClickListener {

            val uri: Uri = Uri.parse(clickedSong.uri)
            if(URLUtil.isValidUrl(uri.toString())) {
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            } else { //Fehlermeldung anzeigen
                val dialogBuilder = AlertDialog.Builder(this)
                with(dialogBuilder) {
                    setTitle("Error")
                    if(clickedSong.uri.isNullOrEmpty()) setMessage("Keine URL hinterlegt.")
                    else setMessage("Fehler beim öffnen von '${clickedSong.uri}'.\nAchtung: URL muss mit http:// beginnen!")
                    setPositiveButton("OK") { _, _ -> }
                    show()
                }
            }
        }

        //SaveChanges Button ausblenden und onClick festlegen
        saveChangesButton.visibility = View.INVISIBLE
        saveChangesButton.setOnClickListener { saveChanges() }
    }

    /**
     * Änderungen in DB speichern
     */
    private fun saveChanges(){
        ObjectBoxUtils.updateDB(
            clickedSong,
            editTextSongName.text.toString(),
            editTextArtistName.text.toString(),
            Keys.valueOf(spinnerKey.selectedItem.toString().replace("#","s")),
            clickedSong.uri
        )
    }

    /**
     * Ungespeicherte Änderungen Dialog anzeigen
     */
    private fun showSaveChangesDialog(){
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Save Changes?")
            setPositiveButton("Yes") {_, _ -> saveChanges(); finish()}

            setNegativeButton("No") {_, _-> finish()}
            show()
        }
    }

    private fun showTypeLayout() {
        when (clickedSong.type) {
            SongTypes.GUITAR -> binding.guitarId.activitySongGuitar.visibility = View.VISIBLE
            SongTypes.PIANO -> binding.pianoId.activitySongPiano.visibility = View.VISIBLE
            else -> {}
        }
    }


    private fun editURIDialog(){
        val dialogBinding : EditUriDialogBinding = EditUriDialogBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(this)
        with(dialogBuilder) {
            setTitle("Edit URI")
            setView(dialogBinding.root)
            setPositiveButton("Save") { _, _ ->
                clickedSong.uri = dialogBinding.editTextURI.text.toString();
                saveChanges()
            }
            setNegativeButton("Cancel") {_, _ -> }
        }

        clickedSong.uri?.let { dialogBinding.editTextURI.setText(clickedSong.uri) }
        dialogBuilder.show()
    }
}