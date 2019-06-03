package com.soundapp.mobile.todotask.data.repository.local

import androidx.room.TypeConverter
import org.threeten.bp.Instant

class TypeConverter {
    @TypeConverter
    fun fromInstant(input: Instant): Long =
        input.toEpochMilli()

    @TypeConverter
    fun fromLong(input: Long): Instant = Instant.ofEpochMilli(input)
}