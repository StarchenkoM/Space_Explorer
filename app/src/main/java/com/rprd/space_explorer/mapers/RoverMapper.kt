package com.rprd.space_explorer.mapers

import com.rprd.space_explorer.data.RoverPhoto
import com.rprd.space_explorer.data.RoverPhotoItemApi
import com.rprd.space_explorer.data.databases.curiositydb.CuriosityPhotoDBEntity
import com.rprd.space_explorer.data.databases.opportunitydb.OpportunityPhotoDBEntity
import javax.inject.Inject

class RoverMapper @Inject constructor() {

    fun mapApiResponseToDatabaseEntity(apiResponse: RoverPhotoItemApi) = CuriosityPhotoDBEntity(
            photoId = apiResponse.photoId,
            sol = apiResponse.sol,
            roverCameraName = apiResponse.roverCamera.cameraName,
            cameraFullName = apiResponse.roverCamera.cameraFullName,
            imageUrl = apiResponse.imageUrl,
            earthDate = apiResponse.earthDate,
            roverName = apiResponse.rover.roverName,
    )

    fun mapApiResponseToCuriosityPhoto(apiResponse: RoverPhotoItemApi) = RoverPhoto(
            photoId = apiResponse.photoId,
            sol = apiResponse.sol,
            roverCameraName = apiResponse.roverCamera.cameraName,
            cameraFullName = apiResponse.roverCamera.cameraFullName,
            imageUrl = apiResponse.imageUrl,
            earthDate = apiResponse.earthDate,
            roverName = apiResponse.rover.roverName,
    )

    fun mapDbEntityToCuriosityPhoto(dbEntity: CuriosityPhotoDBEntity) = RoverPhoto(
            photoId = dbEntity.photoId,
            sol = dbEntity.sol,
            roverCameraName = dbEntity.roverCameraName,
            cameraFullName = dbEntity.cameraFullName,
            imageUrl = dbEntity.imageUrl,
            earthDate = dbEntity.earthDate,
            roverName = dbEntity.roverName,
    )

    fun mapApiResponseToOpportunityDatabaseEntity(apiResponse: RoverPhotoItemApi) = OpportunityPhotoDBEntity(
            photoId = apiResponse.photoId,
            sol = apiResponse.sol,
            roverCameraName = apiResponse.roverCamera.cameraName,
            cameraFullName = apiResponse.roverCamera.cameraFullName,
            imageUrl = apiResponse.imageUrl,
            earthDate = apiResponse.earthDate,
            roverName = apiResponse.rover.roverName,
    )

    fun mapApiResponseToOpportunityPhoto(apiResponse: RoverPhotoItemApi) = RoverPhoto(
            photoId = apiResponse.photoId,
            sol = apiResponse.sol,
            roverCameraName = apiResponse.roverCamera.cameraName,
            cameraFullName = apiResponse.roverCamera.cameraFullName,
            imageUrl = apiResponse.imageUrl,
            earthDate = apiResponse.earthDate,
            roverName = apiResponse.rover.roverName,
    )


    fun mapDbEntityToOpportunityPhoto(dbEntity: OpportunityPhotoDBEntity) = RoverPhoto(
            photoId = dbEntity.photoId,
            sol = dbEntity.sol,
            roverCameraName = dbEntity.roverCameraName,
            cameraFullName = dbEntity.cameraFullName,
            imageUrl = dbEntity.imageUrl,
            earthDate = dbEntity.earthDate,
            roverName = dbEntity.roverName,
    )

}