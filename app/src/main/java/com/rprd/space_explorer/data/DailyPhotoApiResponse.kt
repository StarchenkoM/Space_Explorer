package com.rprd.space_explorer.data

import com.google.gson.annotations.SerializedName

data class DailyPhotoApiResponse(
        @SerializedName("date")
        val pictureDate: String,
        @SerializedName("explanation")
        val explanation: String,
        @SerializedName("media_type")
        val mediaType: String,
        @SerializedName("title")
        val dailyPhotoTitle: String,
        @SerializedName("url")
        val dailyPhotoUrl: String,
)
