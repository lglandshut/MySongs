package com.example.mysongs

import android.os.Parcel
import android.os.Parcelable
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index
import io.objectbox.converter.PropertyConverter
import java.io.Serializable

@Entity
class Song(
    @Id var id: Long,
    @Index var title: String? = null,
    var artist: String? = null,

    @Convert(converter = RoleConverter::class, dbType = String::class)
    var key: Keys? = null
) : Serializable {
}

class RoleConverter : PropertyConverter<Keys?, String?> {
    override fun convertToEntityProperty(databaseValue: String?): Keys? {
        if (databaseValue == null) {
            return null
        }
        for (key in Keys.values()) {
            if (key.key == databaseValue) {
                return key
            }
        }
        return Keys.DEFAULT
    }

    override fun convertToDatabaseValue(entityProperty: Keys?): String? {
        return entityProperty?.key
    }

}