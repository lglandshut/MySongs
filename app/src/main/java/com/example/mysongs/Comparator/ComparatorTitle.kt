package com.example.mysongs.Comparator

import com.example.mysongs.Songs.Song

class ComparatorTitle (private val asc: Boolean = true) : Comparator<Song> {

    override fun compare(o1: Song, o2: Song): Int {
        val title1 = o1.title
        val title2 = o2.title
        val result =
            when {
                title1 == null && title2 == null -> return 0
                title1 == null -> return -1
                title2 == null -> return 1
                else -> title1.compareTo(title2,true)
            }

        return if(asc) result else result *-1
    }
}