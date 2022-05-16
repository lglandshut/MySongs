package com.example.mysongs.Comparator

import com.example.mysongs.Songs.Song
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index

@Entity
class ComparatorContainer(comp: String = "DEFAULT") {

    @Id var id: Long = 0
    @Index var comparator: String = comp
}