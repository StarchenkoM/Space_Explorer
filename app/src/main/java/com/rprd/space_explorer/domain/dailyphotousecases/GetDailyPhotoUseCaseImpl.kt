package com.rprd.space_explorer.domain.dailyphotousecases

import com.rprd.space_explorer.data.DailyPhotoLoadState
import com.rprd.space_explorer.data.repositories.DailyPhotoRepository

class GetDailyPhotoUseCaseImpl(
        private val dailyPhotoRepository: DailyPhotoRepository
) : GetDailyPhotoUseCase {
    override suspend fun getDailyPhoto(date: String): DailyPhotoLoadState =
            dailyPhotoRepository.getDailyPhoto(date)
}