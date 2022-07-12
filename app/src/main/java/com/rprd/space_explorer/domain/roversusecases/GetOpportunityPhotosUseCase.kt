package com.rprd.space_explorer.domain.roversusecases

import com.rprd.space_explorer.data.RoverPhotoLoadState

interface GetOpportunityPhotosUseCase {
    suspend fun getOpportunityPhoto(date: Int): RoverPhotoLoadState
}