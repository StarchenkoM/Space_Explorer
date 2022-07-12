package com.rprd.space_explorer.data.repositories

import com.rprd.space_explorer.data.RoverPhotoItemApi
import com.rprd.space_explorer.data.RoverPhotoLoadState
import com.rprd.space_explorer.data.RoverPhotosApiResponse
import com.rprd.space_explorer.data.databases.curiositydb.CuriosityPhotoDBEntity
import retrofit2.Response

interface CuriosityRepository {
    suspend fun getCuriosityPhotos(date: Int): RoverPhotoLoadState
    suspend fun getCuriosityPhotosApi(sol: Int, page: Int): Response<RoverPhotosApiResponse>
    suspend fun putCuriosityPhotoToDB(roverPhotos: List<RoverPhotoItemApi>)
    fun getCuriosityPhotoFromDB(date: Int): List<CuriosityPhotoDBEntity>
}