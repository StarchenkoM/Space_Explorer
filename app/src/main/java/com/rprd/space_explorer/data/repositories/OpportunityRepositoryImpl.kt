package com.rprd.space_explorer.data.repositories

import com.rprd.space_explorer.data.RoverPhotoItemApi
import com.rprd.space_explorer.data.RoverPhotoLoadState
import com.rprd.space_explorer.data.api.RetrofitInstance
import com.rprd.space_explorer.data.databases.opportunitydb.OpportunityDao
import com.rprd.space_explorer.data.databases.opportunitydb.OpportunityPhotoDBEntity
import com.rprd.space_explorer.mapers.RoverMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpportunityRepositoryImpl @Inject constructor(
        private val roverMapper: RoverMapper,
        private val opportunityDao: OpportunityDao,
) : OpportunityRepository {

    override suspend fun getOpportunityPhotos(date: Int): RoverPhotoLoadState {
        val dbPhotos = getOpportunityPhotoFromDB(date)
        return when {
            dbPhotos.isNotEmpty() -> handleDBLoading(dbPhotos)
            getOpportunityPhotosApi(date, 1).isSuccessful -> handleApiLoading(date)
            else -> RoverPhotoLoadState.LoadError
        }
    }

    private fun handleDBLoading(dbPhotos: List<OpportunityPhotoDBEntity>): RoverPhotoLoadState {
        val result = dbPhotos.map { roverMapper.mapDbEntityToOpportunityPhoto(it) }
        return RoverPhotoLoadState.Success(result)
    }

    private suspend fun handleApiLoading(date: Int): RoverPhotoLoadState {
        val photosApi = getOpportunityPhotosApi(date, 1).body()?.photos
        val result = photosApi?.map { roverMapper.mapApiResponseToOpportunityPhoto(it) }
        photosApi?.let { putOpportunityPhotoToDB(it) }
        return result?.let { RoverPhotoLoadState.Success(it) } ?: RoverPhotoLoadState.LoadError
    }

    override suspend fun getOpportunityPhotosApi(sol: Int, page: Int) =
            RetrofitInstance.api.getOpportunityRoverPhotos(sol, page)


    override suspend fun putOpportunityPhotoToDB(roverPhotos: List<RoverPhotoItemApi>) {
        roverPhotos.forEach {
            val photoEntity = roverMapper.mapApiResponseToOpportunityDatabaseEntity(it)
            opportunityDao.addPhoto(photoEntity)
        }
    }

    override fun getOpportunityPhotoFromDB(date: Int): List<OpportunityPhotoDBEntity> {
        return opportunityDao.getRoverDataNameSol(date)
    }

}