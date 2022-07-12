package com.rprd.space_explorer.domain.dailyphotousecases

import com.rprd.space_explorer.data.FavoritePhotosLoadState
import com.rprd.space_explorer.data.repositories.DailyPhotoRepository

class GetFavoritePhotosStatusUseCaseImpl(
        private val dailyPhotoRepository: DailyPhotoRepository
) : GetFavoritePhotosStatusUseCase {
    override suspend fun getFavoritePhotosStatus(): FavoritePhotosLoadState {
        return dailyPhotoRepository.getFavoritePhotosStatus()
    }
}