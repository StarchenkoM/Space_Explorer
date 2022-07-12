package com.rprd.space_explorer.domain.roversusecases

import com.rprd.space_explorer.data.databases.curiositydb.CuriosityPhotoDBEntity

interface GetRoverPhotosByNameFromDBUseCase {
    fun getRoverPhotoByNameFromDB(roverName: String): List<CuriosityPhotoDBEntity>
}