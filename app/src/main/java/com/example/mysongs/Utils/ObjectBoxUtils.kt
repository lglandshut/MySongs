package com.example.mysongs.Utils

import android.content.Context
import com.example.mysongs.ObjectBox
import com.example.mysongs.Song
import io.objectbox.Box

object ObjectBoxUtils {

    lateinit var songBox: Box<Song>

    fun init(context: Context){
        ObjectBox.init(context)
        songBox = ObjectBox.store.boxFor(Song::class.java)
    }

    fun printDB(){
        val test = songBox.all
        test.forEach{
            println("Song: ${it.artist}, ${it.title}, ${it.id}")
        }
        println(songBox.count())
    }
}