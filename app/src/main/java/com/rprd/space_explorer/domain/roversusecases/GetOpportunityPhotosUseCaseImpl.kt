package com.rprd.space_explorer.domain.roversusecases

import com.rprd.space_explorer.data.RoverPhotoLoadState
import com.rprd.space_explorer.data.repositories.OpportunityRepository

class GetOpportunityPhotosUseCaseImpl(
        private val opportunityRepository: OpportunityRepository,
) : GetOpportunityPhotosUseCase {
    override suspend fun getOpportunityPhoto(date: Int): RoverPhotoLoadState =
            opportunityRepository.getOpportunityPhotos(date)
}