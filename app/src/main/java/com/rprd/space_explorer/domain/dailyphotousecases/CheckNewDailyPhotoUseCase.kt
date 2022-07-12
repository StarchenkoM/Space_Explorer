package com.rprd.space_explorer.domain.dailyphotousecases

interface CheckNewDailyPhotoUseCase {
    suspend fun checkNewDailyPhoto(pictureDate: String): Boolean
}