package com.rprd.space_explorer.data.repositories

import com.rprd.space_explorer.data.DailyPhotoApiResponse
import com.rprd.space_explorer.data.DailyPhotoLoadState
import com.rprd.space_explorer.data.DailyPhotoUpdateState
import com.rprd.space_explorer.data.FavoritePhotosLoadState
import com.rprd.space_explorer.data.databases.dailyphotodb.DailyPhotoDBEntity
import retrofit2.Response

interface DailyPhotoRepository {
    suspend fun getDailyPhoto(date: String): DailyPhotoLoadState
    suspend fun getPhotoFromApi(date: String): Response<DailyPhotoApiResponse>
    suspend fun getDailyPhotoFromDB(date: String): DailyPhotoDBEntity?
    suspend fun updatePhotoFavoriteStatus(date: String, isFavorite: Boolean): DailyPhotoUpdateState
    suspend fun getFavoritePhotosStatus(): FavoritePhotosLoadState
}