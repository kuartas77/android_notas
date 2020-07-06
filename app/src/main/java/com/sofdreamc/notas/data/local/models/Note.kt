package com.sofdreamc.notas.data.local.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

@Parcelize
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String,
    var content: String,
    var important: Boolean,
    var done: Boolean,
    var created: Date? = Date()
) : Parcelable