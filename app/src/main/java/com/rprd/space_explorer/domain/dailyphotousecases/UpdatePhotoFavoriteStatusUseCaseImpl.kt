package com.rprd.space_explorer.domain.dailyphotousecases

import com.rprd.space_explorer.data.DailyPhotoUpdateState
import com.rprd.space_explorer.data.repositories.DailyPhotoRepository

class UpdatePhotoFavoriteStatusUseCaseImpl(
        private val dailyPhotoRepository: DailyPhotoRepository,
) : UpdatePhotoFavoriteStatusUseCase {
    override suspend fun updatePhotoFavoriteStatus(date: String, isFavorite: Boolean): DailyPhotoUpdateState {
        return dailyPhotoRepository.updatePhotoFavoriteStatus(date, isFavorite)
    }
}