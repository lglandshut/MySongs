package com.example.mysongs.Utils

import android.content.Context
import com.example.mysongs.Keys
import com.example.mysongs.ObjectBox
import com.example.mysongs.Song
import com.example.mysongs.Song_.key
import io.objectbox.Box

object ObjectBoxUtils {

    lateinit var songBox: Box<Song>

    fun init(context: Context){
        ObjectBox.init(context)
        songBox = ObjectBox.store.boxFor(Song::class.java)
    }

    fun updateDB(song: Song, title: String? = null, artist: String? = null, keys: Keys? = null){
        val songDB = songBox[song.id]
        title?.let { songDB.title = it }
        artist?.let { songDB.artist = it }
        keys?.let { songDB.key = it}
        songBox.put(songDB)
    }

    fun printDB(){
        val test = songBox.all
        test.forEach{
            println("Song: ${it.title}, Artist: ${it.artist}, ID: ${it.id}")
        }
        println(songBox.count())
    }
}