package com.rprd.space_explorer.mapers

import com.rprd.space_explorer.data.DailyPhoto
import com.rprd.space_explorer.data.DailyPhotoApiResponse
import com.rprd.space_explorer.data.databases.dailyphotodb.DailyPhotoDBEntity
import javax.inject.Inject

class DailyPhotoMapper @Inject constructor() {

    fun mapApiResponseToDatabaseEntity(apiResponse: DailyPhotoApiResponse) = DailyPhotoDBEntity(
            id = 0,
            pictureDate = apiResponse.pictureDate,
            explanation = apiResponse.explanation,
            mediaType = apiResponse.mediaType,
            dailyPhotoTitle = apiResponse.dailyPhotoTitle,
            dailyPhotoUrl = apiResponse.dailyPhotoUrl,
            isFavorite = false,
    )

    fun mapApiResponseToDailyPhoto(apiResponse: DailyPhotoApiResponse) = DailyPhoto(
            pictureDate = apiResponse.pictureDate,
            explanation = apiResponse.explanation,
            mediaType = apiResponse.mediaType,
            dailyPhotoTitle = apiResponse.dailyPhotoTitle,
            dailyPhotoUrl = apiResponse.dailyPhotoUrl,
            isFavorite = false,
    )

    fun mapDataBaseEntityToDailyPhoto(databaseEntity: DailyPhotoDBEntity) = DailyPhoto(
            pictureDate = databaseEntity.pictureDate,
            explanation = databaseEntity.explanation,
            mediaType = databaseEntity.mediaType,
            dailyPhotoTitle = databaseEntity.dailyPhotoTitle,
            dailyPhotoUrl = databaseEntity.dailyPhotoUrl,
            isFavorite = databaseEntity.isFavorite,
    )

}