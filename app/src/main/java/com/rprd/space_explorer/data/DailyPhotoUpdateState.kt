package com.rprd.space_explorer.data

sealed class DailyPhotoUpdateState {
    data class Success(val isFavorite: Boolean) : DailyPhotoUpdateState()
    object Updating : DailyPhotoUpdateState()
    object UpdateError : DailyPhotoUpdateState()
}
