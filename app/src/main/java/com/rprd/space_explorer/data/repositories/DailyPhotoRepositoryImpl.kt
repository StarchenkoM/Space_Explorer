package com.rprd.space_explorer.data.repositories

import com.rprd.space_explorer.data.DailyPhotoApiResponse
import com.rprd.space_explorer.data.DailyPhotoLoadState
import com.rprd.space_explorer.data.DailyPhotoUpdateState
import com.rprd.space_explorer.data.FavoritePhotosLoadState
import com.rprd.space_explorer.data.api.RetrofitInstance
import com.rprd.space_explorer.data.databases.dailyphotodb.DailyPhotoDBEntity
import com.rprd.space_explorer.data.databases.dailyphotodb.DailyPhotoDao
import com.rprd.space_explorer.di.IoCoroutineScope
import com.rprd.space_explorer.mapers.DailyPhotoMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Response
import javax.inject.Inject

class DailyPhotoRepositoryImpl @Inject constructor(
        private val photoMapper: DailyPhotoMapper,
        private val dailyPhotoDao: DailyPhotoDao,
        @IoCoroutineScope private val ioCoroutineScope: CoroutineScope,
) : DailyPhotoRepository {

    override suspend fun getFavoritePhotosStatus(): FavoritePhotosLoadState {
        val favoritePhotos = getFavoritePhotosFromDB()
        return if (favoritePhotos.isNullOrEmpty()) {
            FavoritePhotosLoadState.LoadError
        } else {
            val result = favoritePhotos.map { photoMapper.mapDataBaseEntityToDailyPhoto(it) }
            FavoritePhotosLoadState.Success(result)
        }
    }

    override suspend fun updatePhotoFavoriteStatus(date: String, isFavorite: Boolean): DailyPhotoUpdateState {
        val res = updateFavoriteStatus(date, isFavorite)
        return if (res > 0) DailyPhotoUpdateState.Success(isFavorite) else DailyPhotoUpdateState.UpdateError
    }

    private suspend fun updateFavoriteStatus(date: String, isFavorite: Boolean): Int {
        return ioCoroutineScope.async(Dispatchers.IO) {
            dailyPhotoDao.updateFavoriteField(date, isFavorite)
        }.await()
    }

    override suspend fun getPhotoFromApi(date: String): Response<DailyPhotoApiResponse> {
        return ioCoroutineScope.async(Dispatchers.IO) {
            RetrofitInstance.api.getDailyPhoto(date)
        }.await()
    }

    override suspend fun getDailyPhotoFromDB(date: String): DailyPhotoDBEntity? {
        return ioCoroutineScope.async(Dispatchers.IO) {
            dailyPhotoDao.getDailyPhotoItem(date)
        }.await()
    }

    private suspend fun getFavoritePhotosFromDB(): List<DailyPhotoDBEntity> {
        return ioCoroutineScope.async(Dispatchers.IO) {
            dailyPhotoDao.getFavoritePhotos(isFavorite = true)
        }.await()
    }

    override suspend fun getDailyPhoto(date: String): DailyPhotoLoadState {
        val dbPhotos = getDailyPhotoFromDB(date)
        return when {
            dbPhotos != null -> handleDBLoading(dbPhotos)
            getPhotoFromApi(date).isSuccessful -> handleApiLoading(date)
            else -> DailyPhotoLoadState.PhotoLoadError
        }
    }

    private fun handleDBLoading(dbPhotos: DailyPhotoDBEntity): DailyPhotoLoadState {
        val result = photoMapper.mapDataBaseEntityToDailyPhoto(dbPhotos)
        return DailyPhotoLoadState.Success(result)
    }

    private suspend fun handleApiLoading(date: String): DailyPhotoLoadState {
        val photosApi = getPhotoFromApi(date).body()
        val result = photosApi?.let { photoMapper.mapApiResponseToDailyPhoto(it) }
        photosApi?.let { dailyPhotoDao.addPhoto(photoMapper.mapApiResponseToDatabaseEntity(it)) }
        return result?.let { DailyPhotoLoadState.Success(it) } ?: DailyPhotoLoadState.PhotoLoadError
    }
}