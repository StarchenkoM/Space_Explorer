package com.rprd.space_explorer.data.databases.curiositydb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "curiosity_photos")
data class CuriosityPhotoDBEntity(
        @PrimaryKey
        val photoId: Int,
        val sol: Int,
        val roverCameraName: String,
        val cameraFullName: String,
        val imageUrl: String,
        val earthDate: String,
        val roverName: String,
)