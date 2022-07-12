package com.rprd.space_explorer.data

sealed class DailyPhotoLoadState {
    data class Success(val dailyPhoto: DailyPhoto) : DailyPhotoLoadState()
    object PhotoLoading : DailyPhotoLoadState()
    object PhotoLoadError : DailyPhotoLoadState()
}
