package com.rprd.space_explorer.data.databases.opportunitydb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "opportunity_photos")
data class OpportunityPhotoDBEntity(
        @PrimaryKey(autoGenerate = true)
        val photoId: Int,
        val sol: Int,
        val roverCameraName: String,
        val cameraFullName: String,
        val imageUrl: String,
        val earthDate: String,
        val roverName: String,
)