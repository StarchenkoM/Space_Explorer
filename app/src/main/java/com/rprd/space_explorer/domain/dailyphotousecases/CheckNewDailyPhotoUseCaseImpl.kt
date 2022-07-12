package com.rprd.space_explorer.domain.dailyphotousecases

import com.rprd.space_explorer.data.repositories.DailyPhotoRepository

class CheckNewDailyPhotoUseCaseImpl(
        private val dailyPhotoRepository: DailyPhotoRepository,
) : CheckNewDailyPhotoUseCase {
    override suspend fun checkNewDailyPhoto(pictureDate: String): Boolean {
        val resFromApi = dailyPhotoRepository.getPhotoFromApi(pictureDate)
        val resFromDB = dailyPhotoRepository.getDailyPhotoFromDB(pictureDate)
        return resFromApi.isSuccessful && resFromDB == null
    }
}