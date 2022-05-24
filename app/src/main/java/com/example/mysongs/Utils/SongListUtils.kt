package com.example.mysongs.Utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mysongs.Songs.Song
import com.example.mysongs.Adapter.SonglistAdapter
import com.example.mysongs.Enums.Keys
import com.example.mysongs.Enums.SongTypes
import com.example.mysongs.databinding.ActivityMainBinding
import java.lang.reflect.Type
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

object SongListUtils {

    val songList: MutableList<Song> = ArrayList()

    lateinit var songAdapter: SonglistAdapter
    val searchList: MutableList<Song> = ArrayList()

    fun init(context: Context, binding: ActivityMainBinding){
        songAdapter = SonglistAdapter(songList)
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.adapter = songAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
    }

    fun refreshList() {
        songList.clear()
        songList.addAll(ObjectBoxUtils.songBox.all)
        ObjectBoxUtils.printSongDB()
        songAdapter.notifyDataSetChanged()
        sortSongs(ObjectBoxUtils.comparatorDB())
    }

    fun addSong(song : Song) {
        ObjectBoxUtils.songBox.put(song)
        songList.add(song)
        songAdapter.notifyItemInserted(songList.size + 1)
    }

    fun removeSong(song : Song) {
        val position = songList.indexOf(song)
        println("ZU LÖSCHEN: ${song.title}, POSITION: $position")
        ObjectBoxUtils.songBox.remove(song)
        songList.remove(song)

        songAdapter.notifyItemRemoved(position)
        songAdapter.notifyItemRangeChanged(position, songList.size);
    }

    fun sortSongs(comp: Comparator<Song>) {
        songList.sortWith(comp)
        songAdapter.notifyDataSetChanged()
    }

    fun filter(text: String?) {

        if(text.isNullOrEmpty()) refreshList()
        else {
            val newText = text.lowercase(Locale.getDefault())
            songList.clear()
            songList.addAll(searchList.filter { x ->
                x.title.lowercase().contains(newText) ||
                x.artist?.lowercase()?.contains(newText) == true
            }.toMutableList())
            songList.size
        }
        songAdapter.notifyDataSetChanged()
    }

    fun printSongs(){
        songList.forEach {
            println(it)
        }
    }

    fun generateList(quantity: Int){
        var i = quantity
        while(i > 0){
            val song = Song(0 ,"Song$i", "Artist$i", Keys.A, if(i%2 == 0)SongTypes.GUITAR else SongTypes.PIANO)
            addSong(song)
            i--
        }
    }
}