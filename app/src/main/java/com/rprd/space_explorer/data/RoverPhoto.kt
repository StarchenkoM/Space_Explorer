package com.rprd.space_explorer.data

data class RoverPhoto(
        val photoId: Int,
        val sol: Int,
        val roverCameraName: String,
        val cameraFullName: String,
        val imageUrl: String,
        val earthDate: String,
        val roverName: String,
)