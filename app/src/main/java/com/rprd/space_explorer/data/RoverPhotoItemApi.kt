package com.rprd.space_explorer.data

import com.google.gson.annotations.SerializedName

data class RoverPhotoItemApi(
        @SerializedName("id")
        val photoId: Int,
        @SerializedName("sol")
        val sol: Int,
        @SerializedName("camera")
        val roverCamera: RoverCamera,
        @SerializedName("img_src")
        val imageUrl: String,
        @SerializedName("earth_date")
        val earthDate: String,
        @SerializedName("rover")
        val rover: Rover,
)
