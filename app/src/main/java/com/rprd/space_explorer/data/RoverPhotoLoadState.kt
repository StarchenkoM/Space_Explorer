package com.rprd.space_explorer.data

sealed class RoverPhotoLoadState {
    data class Success(val roverPhotos: List<RoverPhoto>) : RoverPhotoLoadState()
    object PhotosLoading : RoverPhotoLoadState()
    object LoadError : RoverPhotoLoadState()
}