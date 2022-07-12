package com.rprd.space_explorer.domain.dailyphotousecases

import com.rprd.space_explorer.data.DailyPhotoUpdateState

interface UpdatePhotoFavoriteStatusUseCase {
    suspend fun updatePhotoFavoriteStatus(date: String, isFavorite: Boolean): DailyPhotoUpdateState
}