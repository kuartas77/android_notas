package com.sofdreamc.notas.data.local.db

import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun fromDate(value: Date) = value.time

    @TypeConverter
    fun toDate(value: Long) = Date(value)
}