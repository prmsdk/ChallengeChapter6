package com.example.challengechapter6.dao.suit

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SuitEntity(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "player_id") var player_id: Int?,
    @ColumnInfo(name = "mode") var mode: String?,
    @ColumnInfo(name = "hasil") var hasil: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeValue(player_id)
        parcel.writeString(mode)
        parcel.writeString(hasil)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SuitEntity> {
        override fun createFromParcel(parcel: Parcel): SuitEntity {
            return SuitEntity(parcel)
        }

        override fun newArray(size: Int): Array<SuitEntity?> {
            return arrayOfNulls(size)
        }
    }
}