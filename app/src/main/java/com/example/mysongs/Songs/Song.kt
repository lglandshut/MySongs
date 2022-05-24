package com.example.mysongs.Songs

import com.example.mysongs.Enums.Keys
import com.example.mysongs.Enums.SongTypes
import com.example.mysongs.databinding.ActivitySongBinding
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index
import io.objectbox.converter.PropertyConverter
import java.io.Serializable

@Entity
open class Song(
    @Id var id: Long,
    @Index var title: String,
    @Index var artist: String? = null,

    @Convert(converter = KeyConverter::class, dbType = String::class)
    @Index var key: Keys? = null,

    @Convert(converter = TypeConverter::class, dbType = String::class)
    @Index var type: SongTypes? = null,

    @Index var uri: String? = null


) : Serializable {
    open fun showGUI(binding: ActivitySongBinding){}
}

class KeyConverter : PropertyConverter<Keys?, String?> {
    override fun convertToEntityProperty(databaseValue: String?): Keys? {
        if (databaseValue == null) {
            return null
        }
        for (key in Keys.values()) {
            if (key.key == databaseValue) {
                return key
            }
        }
        return Keys.C
    }
    override fun convertToDatabaseValue(entityProperty: Keys?): String? {
        return entityProperty?.key
    }
}

class TypeConverter : PropertyConverter<SongTypes?, String?> {
    override fun convertToEntityProperty(databaseValue: String?): SongTypes? {
        if (databaseValue == null) {
            return null
        }
        for (type in SongTypes.values()) {
            if (type.name == databaseValue) {
                return type
            }
        }
        return null
    }

    override fun convertToDatabaseValue(entityProperty: SongTypes?): String? {
        return entityProperty?.name
    }
}