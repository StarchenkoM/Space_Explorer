package com.rprd.space_explorer.domain.dailyphotousecases

import com.rprd.space_explorer.data.DailyPhotoLoadState

interface GetDailyPhotoUseCase {
    suspend fun getDailyPhoto(date: String): DailyPhotoLoadState
}