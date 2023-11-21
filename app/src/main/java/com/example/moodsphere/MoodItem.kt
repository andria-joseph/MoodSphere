package com.example.moodsphere

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mood_entries")
data class MoodItem(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var select: Int,
    var date: String,
    var details: String
)
