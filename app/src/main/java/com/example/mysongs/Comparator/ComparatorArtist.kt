package com.example.mysongs.Comparator

import com.example.mysongs.Songs.Song

class ComparatorArtist (private val asc: Boolean = true) : Comparator<Song> {
    override fun compare(o1: Song, o2: Song): Int {
        val artist1 = o1.artist
        val artist2 = o2.artist
        val result =
            when {
                artist1 == null && artist2 == null -> return 0
                artist1 == null -> return 1
                artist2 == null -> return -1
                else -> artist1.compareTo(artist2,true)
            }

        return if(asc) result else result *-1
    }
}