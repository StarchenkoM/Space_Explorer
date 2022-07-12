package com.rprd.space_explorer.data

import com.google.gson.annotations.SerializedName

data class RoverPhotosApiResponse(
        @SerializedName("photos")
        val photos: List<RoverPhotoItemApi>
)