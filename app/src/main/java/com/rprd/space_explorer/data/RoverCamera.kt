package com.rprd.space_explorer.data

import com.google.gson.annotations.SerializedName

data class RoverCamera(
        @SerializedName("id")
        val cameraId: Int,
        @SerializedName("name")
        val cameraName: String,
        @SerializedName("rover_id")
        val roverId: Int,
        @SerializedName("full_name")
        val cameraFullName: String,
)