package com.rprd.space_explorer.domain.roversusecases

import com.rprd.space_explorer.data.RoverPhotoLoadState

interface GetCuriosityPhotosUseCase {
    suspend fun getCuriosityPhoto(date: Int): RoverPhotoLoadState
}