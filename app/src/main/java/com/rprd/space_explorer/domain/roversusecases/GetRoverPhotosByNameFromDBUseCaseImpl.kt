package com.rprd.space_explorer.domain.roversusecases

import com.rprd.space_explorer.data.databases.curiositydb.CuriosityPhotoDBEntity
import com.rprd.space_explorer.data.repositories.CuriosityRepository

class GetRoverPhotosByNameFromDBUseCaseImpl(
        private val curiosityRepository: CuriosityRepository,
) : GetRoverPhotosByNameFromDBUseCase {
    override fun getRoverPhotoByNameFromDB(roverName: String): List<CuriosityPhotoDBEntity> {
        return curiosityRepository.getCuriosityPhotoFromDB(1000)
    }
}