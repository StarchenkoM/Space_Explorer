package com.rprd.space_explorer.data.repositories

import com.rprd.space_explorer.data.RoverPhotoItemApi
import com.rprd.space_explorer.data.RoverPhotoLoadState
import com.rprd.space_explorer.data.RoverPhotosApiResponse
import com.rprd.space_explorer.data.databases.opportunitydb.OpportunityPhotoDBEntity
import retrofit2.Response

interface OpportunityRepository {
    suspend fun getOpportunityPhotos(date: Int): RoverPhotoLoadState
    suspend fun getOpportunityPhotosApi(sol: Int, page: Int): Response<RoverPhotosApiResponse>
    suspend fun putOpportunityPhotoToDB(roverPhotos: List<RoverPhotoItemApi>)
    fun getOpportunityPhotoFromDB(date: Int): List<OpportunityPhotoDBEntity>
}