package com.rprd.space_explorer.data

import com.google.gson.annotations.SerializedName

data class Rover(
        @SerializedName("id")
        val roverId: Int,
        @SerializedName("name")
        val roverName: String,
        @SerializedName("landing_date")
        val landingDate: String,
        @SerializedName("launch_date")
        val launchDate: String,
        @SerializedName("status")
        val status: String,
)
