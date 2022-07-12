package com.rprd.space_explorer.data.databases.dailyphotodb

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "daily_photos", indices = [Index(value = ["pictureDate"], unique = true)])
data class DailyPhotoDBEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val pictureDate: String,
        val explanation: String,
        val mediaType: String,
        val dailyPhotoTitle: String,
        val dailyPhotoUrl: String,
        val isFavorite: Boolean = false,
)