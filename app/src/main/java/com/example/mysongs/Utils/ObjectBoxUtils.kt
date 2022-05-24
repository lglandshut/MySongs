package com.example.mysongs.Utils

import android.content.Context
import com.example.mysongs.Comparator.ComparatorArtist
import com.example.mysongs.Comparator.ComparatorContainer
import com.example.mysongs.Comparator.ComparatorTitle
import com.example.mysongs.Enums.Keys
import com.example.mysongs.ObjectBox
import com.example.mysongs.Songs.Song
import io.objectbox.Box

object ObjectBoxUtils {

    lateinit var songBox: Box<Song>
    lateinit var sortBox: Box<ComparatorContainer>

    fun init(context: Context){
        ObjectBox.init(context)
        songBox = ObjectBox.store.boxFor(Song::class.java)
        sortBox = ObjectBox.store.boxFor(ComparatorContainer::class.java)
    }

    fun updateSongDB(song: Song, title: String? = null, artist: String? = null, keys: Keys? = null, link: String? = null){
        val songDB = songBox[song.id]
        title?.let { songDB.title = it }
        artist?.let { songDB.artist = it }
        keys?.let { songDB.key = it}
        link?.let { songDB.uri = it}

        songBox.put(songDB)
    }

    fun updateComparatorDB(comp: String){
        val oldComp = sortBox[1]
        oldComp.comparator = comp
        sortBox.put(oldComp)
    }

    fun comparatorDB() : Comparator<Song> {
        //Wenn DB-leer neuen Comparator anlegen
        if(sortBox.isEmpty) {
            sortBox.put(ComparatorContainer())
            return ComparatorTitle()
        }
        else {
            var compString = sortBox.get(1).comparator
            return when(compString){
                "TitleAsc" -> ComparatorTitle()
                "TitleDesc" -> ComparatorTitle(false)
                "ArtistAsc" -> ComparatorArtist()
                "ArtistDesc" -> ComparatorArtist(false)
                else -> ComparatorTitle()
            }
        }
    }

    fun printSongDB(){
        val test = songBox.all
        test.forEach{
            println("Song: ${it.title}, Artist: ${it.artist}, ID: ${it.id}")
        }
        println(songBox.count())
    }
}