package com.example.moodsphere

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodDao {
    @Insert
    fun insert(moodItem: MoodItem): Long

    @Query("SELECT * FROM mood_entries")
    fun getAllEntries(): Flow<List<MoodItem>>
}