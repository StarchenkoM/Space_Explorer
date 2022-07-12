package com.rprd.space_explorer.data.api


import com.rprd.space_explorer.data.DailyPhotoApiResponse
import com.rprd.space_explorer.data.RoverPhotosApiResponse
import com.rprd.space_explorer.utils.API_KEY_NASA
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {

    @GET("planetary/apod")
    suspend fun getDailyPhoto(
            @Query("date") photo_date: String,
            @Query("api_key") apiKey: String = API_KEY_NASA,
    ): Response<DailyPhotoApiResponse>

    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getCuriosityRoverPhotos(
            @Query("sol") sol: Int,
            @Query("page") page: Int = 1,
            @Query("api_key") apiKey: String = API_KEY_NASA,
    ): Response<RoverPhotosApiResponse>

    @GET("mars-photos/api/v1/rovers/opportunity/photos")
    suspend fun getOpportunityRoverPhotos(
            @Query("sol") sol: Int = DEFAULT_SOL,
            @Query("page") page: Int = 1,
            @Query("api_key") apiKey: String = API_KEY_NASA,
    ): Response<RoverPhotosApiResponse>

    @GET("mars-photos/api/v1/rovers/spirit/photos")
    suspend fun getSpiritRoverPhotos(
            @Query("sol") sol: Int = DEFAULT_SOL,
            @Query("page") page: Int = 1,
            @Query("api_key") apiKey: String = API_KEY_NASA,
    ): Response<RoverPhotosApiResponse>

    companion object {
        private const val DEFAULT_SOL = 1
        private const val ROVER_CAMERA = "all"
        private const val FEED_TYPE = "json"
    }

}