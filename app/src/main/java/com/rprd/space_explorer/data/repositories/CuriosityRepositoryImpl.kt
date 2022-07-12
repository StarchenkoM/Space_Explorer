package com.rprd.space_explorer.data.repositories

import com.rprd.space_explorer.data.RoverPhotoItemApi
import com.rprd.space_explorer.data.RoverPhotoLoadState
import com.rprd.space_explorer.data.api.RetrofitInstance
import com.rprd.space_explorer.data.databases.curiositydb.CuriosityDao
import com.rprd.space_explorer.data.databases.curiositydb.CuriosityPhotoDBEntity
import com.rprd.space_explorer.mapers.RoverMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CuriosityRepositoryImpl @Inject constructor(
        private val roverMapper: RoverMapper,
        private val curiosityDao: CuriosityDao,
) : CuriosityRepository {
    override suspend fun getCuriosityPhotos(date: Int): RoverPhotoLoadState {
        val dbPhotos = getCuriosityPhotoFromDB(date)
        return when {
            dbPhotos.isNotEmpty() -> handleDBLoading(dbPhotos)
            getCuriosityPhotosApi(date, 1).isSuccessful -> handleApiLoading(date)
            else -> RoverPhotoLoadState.LoadError
        }
    }

    private fun handleDBLoading(dbPhotos: List<CuriosityPhotoDBEntity>): RoverPhotoLoadState.Success {
        val result = dbPhotos.map { roverMapper.mapDbEntityToCuriosityPhoto(it) }
        return RoverPhotoLoadState.Success(result)
    }

    private suspend fun handleApiLoading(date: Int): RoverPhotoLoadState {
        val photosApi = getCuriosityPhotosApi(date, 1).body()?.photos
        val result = photosApi?.map { roverMapper.mapApiResponseToCuriosityPhoto(it) }
        photosApi?.let { putCuriosityPhotoToDB(it) }
        return result?.let { RoverPhotoLoadState.Success(it) } ?: RoverPhotoLoadState.LoadError
    }

    override suspend fun getCuriosityPhotosApi(sol: Int, page: Int) =
            RetrofitInstance.api.getCuriosityRoverPhotos(sol, page)


    override suspend fun putCuriosityPhotoToDB(roverPhotos: List<RoverPhotoItemApi>) {
        roverPhotos.forEach {
            val photoEntity = roverMapper.mapApiResponseToDatabaseEntity(it)
            curiosityDao.addPhoto(photoEntity)
        }
    }

    override fun getCuriosityPhotoFromDB(date: Int): List<CuriosityPhotoDBEntity> {
        return curiosityDao.getRoverPhotoByDate(date)
    }

}