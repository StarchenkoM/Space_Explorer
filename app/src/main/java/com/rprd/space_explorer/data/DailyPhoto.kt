package com.rprd.space_explorer.data

data class DailyPhoto(
        val pictureDate: String = "",
        val explanation: String = "",
        val mediaType: String = "",
        val dailyPhotoTitle: String = "",
        val dailyPhotoUrl: String = "",
        val isFavorite: Boolean = false,
)