package com.example.mysongs.Utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mysongs.Songs.Song
import com.example.mysongs.Adapter.SonglistAdapter
import com.example.mysongs.databinding.ActivityMainBinding

object SongListUtils {

    val songList: MutableList<Song> = ArrayList()
    private lateinit var songAdapter: SonglistAdapter

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
        songAdapter.notifyDataSetChanged()
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

    fun printSongs(){
        songList.forEach {
            println(it)
        }
    }
}