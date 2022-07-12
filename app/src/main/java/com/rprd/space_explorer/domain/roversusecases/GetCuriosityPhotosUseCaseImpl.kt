package com.rprd.space_explorer.domain.roversusecases

import com.rprd.space_explorer.data.RoverPhotoLoadState
import com.rprd.space_explorer.data.repositories.CuriosityRepository

class GetCuriosityPhotosUseCaseImpl(
        private val curiosityRepository: CuriosityRepository,
) : GetCuriosityPhotosUseCase {
    override suspend fun getCuriosityPhoto(date: Int): RoverPhotoLoadState =
            curiosityRepository.getCuriosityPhotos(date)
}