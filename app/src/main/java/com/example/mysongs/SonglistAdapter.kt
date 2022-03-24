package com.example.statusboard

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mysongs.Song
import com.example.mysongs.SongActivity
import com.example.mysongs.databinding.SongRowItemBinding

/*
    Adapter for the list of Boards in Main Activity
 */
class SonglistAdapter(list: List<Song>) :
    RecyclerView.Adapter<SonglistAdapter.BoardViewHolder>() {

    private val songList: List<Song> = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BoardViewHolder(SongRowItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        holder.bind(songList[position])
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    inner class BoardViewHolder(itemBinding: SongRowItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private val itemBinding: SongRowItemBinding = itemBinding

        //Befüllt die board_rows im Hauptmenü
        fun bind(song : Song) {
            itemBinding.songName.text = song.title.toString()
            if(song.artist != null)
            itemBinding.songArtist.text = song.artist.toString()
            itemBinding.key.text = song.key!!.key

            itemBinding.root.setOnClickListener { openSong(song) }
        }

        private fun openSong(clickedSong: Song) {
            val context: Context = itemBinding.root.context
            val intent = Intent(context, SongActivity::class.java)
            intent.putExtra("song", clickedSong)
            context.startActivity(intent)
        }

    }

}