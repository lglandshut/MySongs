package com.example.mysongs

import android.os.Parcel
import android.os.Parcelable
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index
import java.io.Serializable

@Entity
class Song(
    @Id var id: Long = 0,
    @Index var title: String? = null,
    var artist: String? = null,
    @Transient var key: Keys? = null
) : Serializable {


}